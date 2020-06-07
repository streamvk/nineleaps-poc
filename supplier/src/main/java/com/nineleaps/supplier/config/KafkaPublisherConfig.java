package com.nineleaps.supplier.config;

/*
 * @Configuration public class KafkaPublisherConfig {
 * 
 * @Bean public ProducerFactory<String, Object> producerFactory() {
 * 
 * Map<String, Object> configs = new HashMap<>();
 * 
 * configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
 * configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
 * StringSerializer.class);
 * configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
 * JsonSerializer.class);
 * 
 * return new DefaultKafkaProducerFactory<>(configs); }
 * 
 * @Bean public KafkaTemplate<String, Object> kafkaTemplate() { return new
 * KafkaTemplate<>(producerFactory()); } }
 */