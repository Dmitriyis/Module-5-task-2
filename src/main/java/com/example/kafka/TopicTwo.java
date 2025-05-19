package com.example.kafka;

public class TopicTwo {
    private String color;

    private String type = "topic-2";

    public TopicTwo() {
    }

    public TopicTwo(String color, String type) {
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
        return "TopicTwo{" +
                "color='" + color + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
