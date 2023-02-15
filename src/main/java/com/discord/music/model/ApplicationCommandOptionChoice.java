package com.discord.music.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record ApplicationCommandOptionChoice(
        String name,
        @JsonProperty("name_localizations")
        Map<String, String> nameLocalizations,
        Object value
) { }
