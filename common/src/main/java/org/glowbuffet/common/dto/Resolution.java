package org.glowbuffet.common.dto;

import java.util.UUID;

public class Resolution {
    private UUID id;
    private String text;
    private long chatId;

    public Resolution() {}

    public Resolution(Command command) {
        this.id = UUID.randomUUID();
        this.text = command.getText();
        this.chatId = command.getChatId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}