package com.example.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ProducerServiceTopicTwo {

    private static final Logger log = LoggerFactory.getLogger(ProducerServiceTopicOne.class);

    public final KafkaTemplate<String, TopicTwo> kafkaTemplate;

    public ProducerServiceTopicTwo(KafkaTemplate<String, TopicTwo> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        TopicTwo topicTwo = new TopicTwo();
        topicTwo.setColor(message + new Random().nextInt());

        try {
            kafkaTemplate.send("topic-2", topicTwo).get();
        } catch (Exception ex) {
            log.debug("Ошибка отправки сообщения в топик [topic-2]. " + ex.getMessage());
            throw new RuntimeException("Ошибка отправки сообщения в топик [topic-2]. " + ex.getMessage());
        }

        log.debug("Сообщение отправлено в топик [topic-2]. Message: " + topicTwo);
    }
}
