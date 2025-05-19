package com.example.kafka;

public class TopicOne {
    private String color;

    private String type = "topic-1";

    public TopicOne() {
    }

    public TopicOne(String color, String type) {
        this.color = color;
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TopicOne{" +
                "color='" + color + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
