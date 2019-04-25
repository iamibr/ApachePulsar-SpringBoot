# ApachePulsar-SpringBoot
Its Work in progress project


Change SERVICE_URL value of running pulsar instance in PulsarClientImpl file
Ex: SERVICE_URL = "pulsar://localhost:6650";

Apache Pulsar in Spring Boot Implementations for Producer and Cosumer creation.

POST http://localhost:8080/pulsar/createProducer
{
  "producerName": "Shared-producer1",
  "topic": "new-Shared-topic1",
  "msg": [ "jav","hhgv" ]
}

POST http://localhost:8080/pulsar/createConsumers
{
  "topicName": "new-Shared-topic1",
  "subscriptionName": "new-shared-subscription1",
  "subscriptionType": "Shared",
  "noOfConsumers": "5"
}
