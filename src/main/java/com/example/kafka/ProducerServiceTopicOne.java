package com.example.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ProducerServiceTopicOne {

    private static final Logger log = LoggerFactory.getLogger(ProducerServiceTopicOne.class);

    public final KafkaTemplate<String, TopicOne> kafkaTemplate;

    public ProducerServiceTopicOne(KafkaTemplate<String, TopicOne> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        TopicOne topicOne = new TopicOne();
        topicOne.setColor(message + new Random().nextInt());

        try {
            kafkaTemplate.send("topic-1", topicOne).get();
        } catch (Exception ex) {
            log.debug("Ошибка отправки сообщения в топик [topic-1]. " + ex.getMessage());
            throw new RuntimeException("Ошибка отправки сообщения в топик [topic-1]. " + ex.getMessage());
        }

        log.debug("Сообщение отправлено в топик [topic-1]. Message: " + topicOne);
    }
}
