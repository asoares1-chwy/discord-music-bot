package com.discord.music.model;

public class DiscordInteractionResponse {
    InteractionResponseType type;

    public DiscordInteractionResponse(InteractionResponseType type) {
        this.type = type;
    }

    public InteractionResponseType getType() {
        return type;
    }

    public void setType(InteractionResponseType type) {
        this.type = type;
    }
}
