package com.discord.music.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DiscordUser(
        String avatar,
        String avatarDecoration,
        String discriminator,
        String displayName,
        String id,
        int publicFlags,
        String username
) {
}