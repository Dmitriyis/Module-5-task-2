package com.example.kafka.config;

import com.example.kafka.TopicOne;
import com.example.kafka.TopicTwo;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    private static final Logger log = LoggerFactory.getLogger(KafkaConfig.class);

    // Topic
    @Bean
    public NewTopic newTopicOne() {
        return TopicBuilder.name("topic-1")
                .replicas(3)
                .partitions(3)
                .config("retention.ms", "604800000")
                .config("min.insync.replicas", "2")
                .build();
    }

    @Bean
    public NewTopic newTopicTwo() {
        return TopicBuilder.name("topic-2")
                .replicas(3)
                .partitions(3)
                .config("retention.ms", "604800000")
                .config("min.insync.replicas", "2")
                .build();
    }

    // Producer
    @Bean
    public ProducerFactory<String, TopicOne> producerFactoryTopicOne(KafkaProperties kafkaProperties) {
        HashMap<String, Object> configProps = new HashMap<>(kafkaProperties.buildProducerProperties());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");

        configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        configProps.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        configProps.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required " +
                "username=\"producer\" password=\"12345678\";");

        configProps.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        configProps.putIfAbsent(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "/etc/kafka/secrets/client.truststore.jks");
        configProps.putIfAbsent(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "12345678");
        configProps.putIfAbsent(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, TopicOne> kafkaTemplateTopicOne(ProducerFactory<String, TopicOne> kafkaProducer) {
        return new KafkaTemplate<>(kafkaProducer);
    }

    @Bean
    public ProducerFactory<String, TopicTwo> producerFactoryTopicTwo(KafkaProperties kafkaProperties) {
        HashMap<String, Object> configProps = new HashMap<>(kafkaProperties.buildConsumerProperties());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");

        configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        configProps.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        configProps.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required " +
                "username=\"producer\" password=\"12345678\";");

        configProps.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        configProps.putIfAbsent(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "/etc/kafka/secrets/client.truststore.jks");
        configProps.putIfAbsent(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "12345678");
        configProps.putIfAbsent(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, TopicTwo> kafkaTemplateTopicTwo(ProducerFactory<String, TopicTwo> kafkaProducer) {
        return new KafkaTemplate<>(kafkaProducer);
    }

    // Consumer
    @Bean
    public ConsumerFactory<String, TopicOne> topicOneConsumer(KafkaProperties kafkaProperties) {
        HashMap<String, Object> configProps = new HashMap<>(kafkaProperties.buildConsumerProperties());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TYPE_MAPPINGS, "topicOne:com.example.kafka.TopicOne");
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        configProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");
        configProps.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "3000");
        configProps.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 200);

        configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        configProps.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        configProps.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required " +
                "username=\"consumer\" password=\"12345678\";");

        configProps.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        configProps.putIfAbsent(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "/etc/kafka/secrets/client.truststore.jks");
        configProps.putIfAbsent(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "12345678");
        configProps.putIfAbsent(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");

        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TopicOne> kafkaListenerContainerFactoryTopicOneConsumer(ConsumerFactory<String, TopicOne> consumerFactory, DefaultErrorHandler errorHandler) {
        ConcurrentKafkaListenerContainerFactory<String, TopicOne> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, TopicTwo> topicTwoConsumer(KafkaProperties kafkaProperties) {
        HashMap<String, Object> configProps = new HashMap<>(kafkaProperties.buildConsumerProperties());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TYPE_MAPPINGS, "topicTwo:com.example.kafka.TopicTwo");
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        configProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");
        configProps.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "3000");


        configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        configProps.put(SaslConfigs.SASL_MECHANISM, "PLAIN");

        configProps.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required " +
                "username=\"consumer\" password=\"12345678\";");

        configProps.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        configProps.putIfAbsent(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "/etc/kafka/secrets/client.truststore.jks");
        configProps.putIfAbsent(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "12345678");
        configProps.putIfAbsent(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");

        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TopicTwo> kafkaListenerContainerFactoryTopicTwoConsumer(ConsumerFactory<String, TopicTwo> consumerFactory, DefaultErrorHandler errorHandler) {
        ConcurrentKafkaListenerContainerFactory<String, TopicTwo> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }

    // Handler
    @Bean
    public DefaultErrorHandler kafkaErrorHandler() {
        DefaultErrorHandler handler = new DefaultErrorHandler(
                (record, exception) -> {
                    log.info("[ОШИБКА] Не удалось обработать сообщение: " +
                            "Топик=" + record.topic() +
                            ", Ключ=" + record.key() +
                            ", Ошибка=" + exception.getCause());
                },
                new FixedBackOff(2000L, 5)
        );

        handler.setAckAfterHandle(false);
        handler.addNotRetryableExceptions(Exception.class);

        return handler;
    }

    // KafkaAdmin

    @Bean
    public KafkaAdmin kafkaAdmin(KafkaProperties kafkaProperties) {
        Map<String, Object> adminProps = kafkaProperties.buildAdminProperties(null);
        adminProps.putIfAbsent("client.id", "admin-client");
        adminProps.putIfAbsent("bootstrap.servers", "kafka-1:9093");
        adminProps.putIfAbsent(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "/etc/kafka/secrets/client.truststore.jks");
        adminProps.putIfAbsent(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "12345678");
        adminProps.putIfAbsent(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        adminProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        adminProps.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        adminProps.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required " +
                "username=\"admin\" password=\"admin-secret\";");

        return new KafkaAdmin(adminProps);
    }
}
