package com.example.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class TopicOneConsumerService {
    private static final Logger log = LoggerFactory.getLogger(TopicOneConsumerService.class);

    @KafkaListener(topics = "topic-1", groupId = "topic-1", containerFactory = "kafkaListenerContainerFactoryTopicOneConsumer")
    public void listenStringMessage(@Payload TopicOne topicOne) {
        log.info("Получено сообщение из топика topic-1. Message: " + topicOne);
    }
}

