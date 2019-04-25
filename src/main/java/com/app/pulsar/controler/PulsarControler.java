package com.app.pulsar.controler;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.ConsumerBuilder;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.SubscriptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.pulsar.model.PulsarProducerModel;
import com.app.pulsar.model.PulsarConsumerModel;
import com.app.pulsar.provGateway.PulsarConsumerImpl;
import com.app.pulsar.provGateway.PulsarProducerImpl;

@RestController
@RequestMapping("/pulsar")
public class PulsarControler {
	private static final Logger log = LoggerFactory.getLogger(PulsarControler.class);

	@Autowired
	private PulsarProducerImpl pulsarProducer;
	@Autowired
	private PulsarConsumerImpl pulsarConsumer;

	@PostMapping(path = "/sendMessage", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> sendMessage(@RequestBody PulsarProducerModel pulsaProducerModel) throws Exception {
		Producer<String> pulsarproducer = pulsarProducer.createProducer(pulsaProducerModel);
		for (String msg : pulsaProducerModel.getMsg()) {
			pulsarproducer.sendAsync(msg).thenAccept(msgId -> {
				log.info("Message with ID %s successfully sent", msgId);
			});
		}
		if (pulsarproducer != null) {
			return ResponseEntity.ok(pulsaProducerModel);
		} else {
			return new ResponseEntity<Object>(new String("Error in  producer"), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/getMessage", produces = "application/json")
	public ResponseEntity<Object> getMessage(@RequestParam String topic_name, @RequestParam String subscription_name)
			throws Exception {
		CompletableFuture<Consumer<String>> consumer = pulsarConsumer.createConsumer(topic_name, subscription_name);
		CompletableFuture<Message<String>> msg = consumer.get().receiveAsync();
		String content = new String(msg.get().getData());
		System.out.println("content= " + content + " msgId=" + msg.get().getMessageId());
		{
			consumer.get().acknowledgeAsync(msg.get().getMessageId());
			return ResponseEntity.ok(content);
		}
	}

	@PostMapping(path = "/createConsumers", consumes = "application/json")
	public ResponseEntity<Object> createCustomerWithBuilder(@RequestBody PulsarConsumerModel pulsarConsumerModel)
			throws Exception {
		if (pulsarConsumerModel.getSubscriptionType().equals(SubscriptionType.Exclusive)
				&& pulsarConsumerModel.getNoOfConsumers() > 1) {
			return new ResponseEntity<Object>(new String("Exclusive Type can have only 1 consumer"),
					HttpStatus.BAD_REQUEST);
		} else {
			List<ConsumerBuilder<byte[]>> consumerBuilderList = pulsarConsumer
					.createCustomerWithBuilder(pulsarConsumerModel);
			if (consumerBuilderList != null) {
				return ResponseEntity.ok(consumerBuilderList);
			} else {
				return new ResponseEntity<Object>(new String("Error in creating customer"), HttpStatus.BAD_REQUEST);
			}
		}

	}

	@PostMapping(path = "/createProducer", consumes = "application/json")
	public ResponseEntity<Object> createProducerWithBuilder(@RequestBody PulsarProducerModel pulsarProducerModel)
			throws Exception {
		Producer<String> pulsarproducer = pulsarProducer.createProducer(pulsarProducerModel);
		for (String msg : pulsarProducerModel.getMsg()) {
			pulsarproducer.sendAsync(msg).thenAccept(msgId -> {
				log.info("Message with ID %s successfully sent", msgId);
			});
		}
		if (pulsarproducer != null) {
			return ResponseEntity.ok(pulsarproducer);
		} else {
			return new ResponseEntity<Object>(new String("Error in  producer"), HttpStatus.BAD_REQUEST);
		}
	}

}