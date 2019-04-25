package com.app.pulsar.provGateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.ConsumerBuilder;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.app.pulsar.model.PulsarConsumerModel;

@Component
public class PulsarConsumerImpl {
	private static final Logger log = LoggerFactory.getLogger(PulsarConsumerImpl.class);
	
    public CompletableFuture<Consumer<String>> createConsumer( String topic_name, String subscription_name) throws IOException, InterruptedException, ExecutionException {
    	
        CompletableFuture<Consumer<String>> consumer = PulsarClientImpl.getInstance().newConsumer(Schema.STRING)
                .topic(topic_name)
               // .consumerName("4e000")
                .subscriptionType(SubscriptionType.Shared)
                .subscriptionName(subscription_name)
                .ackTimeout(10, TimeUnit.SECONDS)
                .subscribeAsync();
        log.info("Created Consumer",consumer.toString());
        log.info("consumer for the topic ", topic_name);
        log.info("Consumer Subscription", consumer.get().getSubscription());
		return consumer;
    }
    
    public  List<ConsumerBuilder<byte[]>> createCustomerWithBuilder(PulsarConsumerModel pulsarConsumerModel) {
    	List<ConsumerBuilder<byte[]>> consumerBuilderList = new ArrayList<ConsumerBuilder<byte[]>>();
    	ConsumerBuilder<byte[]> consumerBuilder = PulsarClientImpl.getInstance().newConsumer()
		        .topic(pulsarConsumerModel.getTopicName())
		        .subscriptionName(pulsarConsumerModel.getSubscriptionName())
		        .subscriptionType(pulsarConsumerModel.getSubscriptionType());
		IntStream.range(0, pulsarConsumerModel.getNoOfConsumers()).forEach(i -> {
			try {
				consumerBuilder
				   .consumerName(String.format("consumer-%d", i))
				   .subscribe();
				consumerBuilderList.add(consumerBuilder);
			} catch (PulsarClientException e) {
				log.error(e.getMessage());
			}
         
			
		});
		log.info("Consumer created for topic {}", pulsarConsumerModel.getTopicName());
		return consumerBuilderList;
    

		
    	
    }
    
    

}
