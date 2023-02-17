package com.discord.music.model;

public record InteractionResponse(InteractionResponseType type, InteractionResponseData data) {
    public InteractionResponse(InteractionResponseType type) {
        this(type, null);
    }
}
