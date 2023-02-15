package com.discord.music.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ApplicationCommandType {
    CHAT_INPUT(1),
    USER(2),
    MESSAGE(3);

    private final int value;
    ApplicationCommandType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }
}
