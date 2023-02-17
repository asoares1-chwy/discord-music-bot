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
        Integer minValue,
        @JsonProperty("max_value")
        Integer maxValue,
        @JsonProperty("min_length")
        Integer minLength,
        @JsonProperty("max_length")
        Integer maxLength,
        boolean autocomplete
) {
        public ApplicationCommandOption(
                ApplicationCommandOptionType type,
                String name,
                String description,
                boolean required,
                List<ApplicationCommandOptionChoice> choices
        ) {
                this(type, name, Map.of(), description, Map.of(), List.of(), List.of(), choices, required,
                        null, null, null, null, false);
        }
}
