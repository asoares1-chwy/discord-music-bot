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
        int minValue,
        int maxValue,
        int minLength,
        int maxLength,
        boolean autocomplete
) { }
