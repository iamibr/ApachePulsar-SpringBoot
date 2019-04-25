package com.app.pulsar.model;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
@SuppressWarnings("serial")
@Component
public class PulsarProducerDeserializer extends StdDeserializer<PulsarProducerModel>{

	protected PulsarProducerDeserializer(Class<?> vc) {
		super(vc);
		
	}
	
	@Override
	public PulsarProducerModel deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		final PulsarProducerModel pulsarProducerModel = new PulsarProducerModel();
		final JsonNode node = p.getCodec().readTree(p);
		if (node != null) {
			pulsarProducerModel.setProducerName(getStringFromArrNode(node.get(pulsarProducerModel.PRODUCERNAME)));
			pulsarProducerModel.setTopic(getStringFromArrNode(node.get(pulsarProducerModel.TOPIC)));
			final List<String> instanceList = new ArrayList<String>();
			final JsonNode runningAppArrayNode = node.get(pulsarProducerModel.MSG);
			if (runningAppArrayNode != null && runningAppArrayNode instanceof ArrayNode) {
				final ArrayNode arrayNode = (ArrayNode) runningAppArrayNode;
				arrayNode.elements().forEachRemaining((x) -> {
					getListFromString(getStringFromArrNode(x))
							.forEach(y -> instanceList.add(y.trim()));
				});
			} else if(runningAppArrayNode != null){
				getListFromString(getStringFromArrNode(node.get(pulsarProducerModel.MSG)))
						.forEach(y -> instanceList.add(y.toString().trim()));
			}
			pulsarProducerModel.setMsg((instanceList));
		}
		return pulsarProducerModel;
	}
	
	public static String getStringFromArrNode(final JsonNode node) {
		String value = null;
		if (node != null && !(node instanceof NullNode)) {
			if (node instanceof ArrayNode && ((ArrayNode) node).size() > 0) {
				value = ((ArrayNode) node).get(0).asText();
			} else {
				value = node.asText();
			}
		}
		return value;
	}
	
	private List<String> getListFromString(String cap) {
		List<String> capabilityList = new ArrayList<>();
		for (String x : cap.split(",")) {
			if (x.contains("[")) {
				x = x.replace('[', ' ');
			}
			if (x.contains("\"")) {
				x = x.replace('\"', ' ');
			}
			if (x.contains("]")) {
				x = x.replace(']', ' ');
			}
			capabilityList.add(x.trim());
		}
		return capabilityList;
	}

	public PulsarProducerDeserializer() {
		super(PulsarProducerModel.class);
		
	}

}
