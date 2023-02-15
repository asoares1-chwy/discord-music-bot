package com.discord.music.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record ApplicationCommandOption(
        ApplicationCommandOptionType type,
        String name,
        @JsonProperty("name_localizations")
        Map<String, String> nameLocalizations,
        String description,
        @JsonProperty("description_localizations")
        Map<String, String> descriptionLocalizations,
        List<ApplicationCommandOption> options,
        @JsonProperty("channel_types")
        List<ChannelType> channelTypes,
        List<ApplicationCommandOptionChoice> choices,
        boolean required,
        @JsonProperty("min_value")
        int minValue,
        @JsonProperty("max_value")
        int maxValue,
        @JsonProperty("min_length")
        int minLength,
        @JsonProperty("max_length")
        int maxLength,
        boolean autocomplete
) { }
