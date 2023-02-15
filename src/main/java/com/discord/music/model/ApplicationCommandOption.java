package com.discord.music.model;

import java.util.List;
import java.util.Map;

public record ApplicationCommandOption(
        ApplicationCommandOptionType type,
        String name,
        Map<String, String> nameLocalizations,
        String description,
        Map<String, String> descriptionLocalizations,
        List<ApplicationCommandOption> options,
        List<ChannelType> channelTypes,
        List<ApplicationCommandOptionChoice> choices,
        boolean required,
        int minValue,
        int maxValue,
        int minLength,
        int maxLength,
        boolean autocomplete
) { }
