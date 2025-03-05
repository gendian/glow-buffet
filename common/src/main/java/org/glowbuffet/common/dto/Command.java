package org.glowbuffet.common.dto;

import com.pengrad.telegrambot.model.Message;

import java.util.UUID;

public class Command {
    private UUID id;
    private String text;
    private long chatId;

    public Command() {}

    public Command(Message message) {
        this.id = UUID.randomUUID();
        this.text = message.text();
        this.chatId = message.from().id();
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
