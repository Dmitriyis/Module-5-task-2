package com.example.kafka;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final ProducerServiceTopicOne producerServiceTopicOne;
    private final ProducerServiceTopicTwo producerServiceTopicTwo;

    public RestController(ProducerServiceTopicOne producerServiceTopicOne, ProducerServiceTopicTwo producerServiceTopicTwo) {
        this.producerServiceTopicOne = producerServiceTopicOne;
        this.producerServiceTopicTwo = producerServiceTopicTwo;
    }


    @PostMapping("create-topic-1/{color}")
    public String createTopicOne(@PathVariable(value = "color") String color) {
        producerServiceTopicOne.sendMessage(color);
        return "Ok!";
    }

    @PostMapping("create-topic-2/{color}")
    public String createTopicTwo(@PathVariable(value = "color") String color) {
        producerServiceTopicTwo.sendMessage(color);
        return "Ok!";
    }
}
