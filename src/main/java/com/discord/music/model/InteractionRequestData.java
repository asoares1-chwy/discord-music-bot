package com.discord.music.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record InteractionRequestData(
        String id,
        String name,
        ApplicationCommandType type,
        List<ApplicationCommandOption> options,
        @JsonProperty("guild_id")
        String guildId,
        @JsonProperty("target_id")
        String targetId
) {
}
