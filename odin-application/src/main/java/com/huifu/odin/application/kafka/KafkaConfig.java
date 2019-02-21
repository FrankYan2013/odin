package com.huifu.odin.application.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author frank
 */
@Configuration
public class KafkaConfig {

    @Value("${kafka.producer.servers:172.31.13.172:9092,172.31.13.173:9092,172.31.13.174:9092}")
    private String servers;
    @Value("${kafka.producer.retries:0}")
    private int retries;
    @Value("${kafka.producer.batch.size:4096}")
    private int batchSize;
    @Value("${kafka.producer.linger:1}")
    private int linger;
    @Value("${kafka.producer.buffer.memory:40960}")
    private int bufferMemory;
    @Value("${kafka.producer.request.timeout:2000}")
    private int requestTimeOutMs;
    @Value("${kafka.producer.request.acks:0}")
    private String acks;
    @Value("${kafka.producer.request.max.block.ms:500}")
    private int maxBlockMs;


    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeOutMs);
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, maxBlockMs);
        return props;
    }

    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
