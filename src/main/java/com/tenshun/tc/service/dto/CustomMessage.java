package com.tenshun.tc.service.dto;


public class CustomMessage {

    private String text;

    public CustomMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "CustomMessage{" +
                "text='" + text + '\'' +
                '}';
    }
}
