package com.discord.music.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * More fields are available, but not required for bot usage.
 */
public record InteractionResponseData(String content, @JsonProperty("tts") boolean textToSpeech) {
    public InteractionResponseData(String content) {
        this(content, false);
    }
}
