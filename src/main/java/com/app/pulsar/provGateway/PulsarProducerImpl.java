package com.app.pulsar.provGateway;

import java.io.IOException;

import org.apache.pulsar.client.api.CompressionType;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.Schema;
import org.springframework.stereotype.Component;

import com.app.pulsar.model.PulsarProducerModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PulsarProducerImpl {

	private static final Logger log = LoggerFactory.getLogger(PulsarProducerImpl.class);

	public Producer<String> createProducer( PulsarProducerModel pulsarProducerModel) throws IOException {
		Producer<String> producer = PulsarClientImpl.getInstance().newProducer(Schema.STRING)
				.topic(pulsarProducerModel.getTopic())
				.producerName(pulsarProducerModel.getProducerName())
				.compressionType(CompressionType.LZ4).create();
		log.info("Created producer", producer);
		return producer;

	}
	
	
}