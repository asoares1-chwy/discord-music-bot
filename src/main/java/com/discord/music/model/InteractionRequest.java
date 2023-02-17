package com.discord.music.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InteractionRequest(
        String id,
        String applicationId,
        InteractionType type,
        InteractionRequestData data,
        @JsonProperty("guild_id")
        String guildId,
        @JsonProperty("channel_id")
        String channelId,
        @JsonProperty("guild_member")
        DiscordGuildMember guildMember,
        //DiscordUser user,
        String token,
        int version,
        DiscordMessage message,
        @JsonProperty("app_permissions")
        String appPermissions,
        String locale,
        @JsonProperty("guild_locale")
        String guildLocale
) {
}
