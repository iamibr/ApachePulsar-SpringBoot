package com.app.pulsar.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@SuppressWarnings("serial")
public class PulsarProducerSerializer extends StdSerializer<PulsarProducerModel> {

	protected PulsarProducerSerializer(final Class<PulsarProducerModel> t) {
		super(t);

	}

	public PulsarProducerSerializer() {
		super(PulsarProducerModel.class);
	}

	@Override
	public void serialize(PulsarProducerModel value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException {
		jgen.writeStartObject();
		if (value != null) {
			// 1. id
			if (value.getProducerName() != null) {
				jgen.writeStringField(value.PRODUCERNAME, value.getProducerName());
			} else {
				jgen.writeNullField(value.PRODUCERNAME);
			}
			if (value.getTopic() != null) {
				jgen.writeStringField(value.TOPIC, value.getTopic());
			} else {
				jgen.writeNullField(value.TOPIC);
			}
			if (value.getMsg() != null && !value.getMsg().isEmpty()) {
				jgen.writeArrayFieldStart(value.MSG);
				value.getMsg().forEach((stackInstance) -> {
					try {
						jgen.writeObject(stackInstance);
					} catch (final IOException ex) {
					}
				});
				jgen.writeEndArray();
			} else {
				jgen.writeNullField(value.MSG);
			}

		}
		jgen.writeEndObject();
		

	}

}
