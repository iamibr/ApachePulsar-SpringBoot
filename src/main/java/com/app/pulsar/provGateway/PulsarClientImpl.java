package com.app.pulsar.provGateway;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class PulsarClientImpl {

	private static final String SERVICE_URL = "pulsar://192.168.10.10:6650";
	private PulsarClientImpl() {
	}

	private static class PulsarClientObject {
		private static final PulsarClient pulsarClient;
		static {
			try{
				pulsarClient = PulsarClient.builder().serviceUrl(SERVICE_URL).build();
			}catch(PulsarClientException e) {
				throw new ExceptionInInitializerError(e);
			}
			
		}
	}
	 public static PulsarClient getInstance(){
	        return PulsarClientObject.pulsarClient;
	    }

}
