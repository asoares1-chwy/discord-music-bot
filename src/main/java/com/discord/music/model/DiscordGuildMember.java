package com.discord.music.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;

public record DiscordGuildMember(
        DiscordUser user,
        String nick,
        String avatar,
        List<String> roles,
        @JsonProperty("joined_at")
        OffsetDateTime joinedAt,
        @JsonProperty("premium_since")
        OffsetDateTime premiumSince,
        boolean deaf,
        boolean mute,
        int flags,
        boolean pending,
        String permissions,
        @JsonProperty("communication_disabled_until")
        OffsetDateTime communicationDisabledUntil
) {
}
