package com.discord.music.model;

import java.util.Map;

public record ApplicationCommandOptionChoice(
        String name,
        Map<String, String> nameLocalizations,
        Object value
) { }
