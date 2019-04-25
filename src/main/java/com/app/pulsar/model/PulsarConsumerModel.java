package com.app.pulsar.model;
import org.apache.pulsar.client.api.SubscriptionType;
public class PulsarConsumerModel {
 
	private String topicName;
	private String subscriptionName;
	private SubscriptionType subscriptionType;
	private int noOfConsumers;
	
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getSubscriptionName() {
		return subscriptionName;
	}
	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}
	public SubscriptionType getSubscriptionType() {
		return subscriptionType;
	}
	public void setSubscriptionType(SubscriptionType subscriptionType) {
		this.subscriptionType = subscriptionType;
	}
	public int getNoOfConsumers() {
		return noOfConsumers;
	}
	public void setNoOfConsumers(int noOfConsumers) {
		this.noOfConsumers = noOfConsumers;
	}
	
	public PulsarConsumerModel(String topicName, String subscriptionName, SubscriptionType subscriptionType,
			int noOfConsumers) {
		super();
		this.topicName = topicName;
		this.subscriptionName = subscriptionName;
		this.subscriptionType = subscriptionType;
		this.noOfConsumers = noOfConsumers;
	}
	
	@Override
	public String toString() {
		return "PulsarConsumerModel [topicName=" + topicName + ", subscriptionName=" + subscriptionName
				+ ", subscriptionType=" + subscriptionType + ", noOfConsumers=" + noOfConsumers + "]";
	}
	
	public PulsarConsumerModel() {
		super();
	}
	
	


}
