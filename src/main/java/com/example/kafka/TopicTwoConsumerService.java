package com.example.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicTwoConsumerService {

    private static final Logger log = LoggerFactory.getLogger(TopicTwoConsumerService.class);

    @KafkaListener(topics = "topic-2", groupId = "topic-2", containerFactory = "kafkaListenerContainerFactoryTopicTwoConsumer")
    public void listenStringMessage(@Payload TopicTwo topicTwo) {
        log.info("Получено сообщения из топика topic-2. Message:" + topicTwo);
    }
}
