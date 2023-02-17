package com.discord.music.model;

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