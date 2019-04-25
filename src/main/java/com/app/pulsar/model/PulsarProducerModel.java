package com.app.pulsar.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@SuppressWarnings("serial")
//@JsonSerialize(using = PulsarProducerSerializer.class)
@JsonDeserialize(using = PulsarProducerDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PulsarProducerModel implements Serializable {

	public static final String PRODUCERNAME = "producerName";
	@JsonProperty
	private String producerName;
	public static final String TOPIC = "topic";
	@JsonProperty
	private String topic;
	public static final String MSG = "msg";
	@JsonProperty
	private List<String> msg;	

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public PulsarProducerModel() {
		super();

	}

	public List<String> getMsg() {
		return msg;
	}

	public void setMsg(List<String> msg) {
		this.msg = msg;
	}

	public PulsarProducerModel(String producerName, String topic, List<String> msg) {
		super();
		this.producerName = producerName;
		this.topic = topic;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "PulsarProducerModel [producerName=" + producerName + ", topic=" + topic + ", msg=" + msg + "]";
	}

}
