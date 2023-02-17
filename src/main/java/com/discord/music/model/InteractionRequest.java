package com.discord.music.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InteractionRequest(
        String id,
        @JsonProperty("application_id")
        String applicationId,
        InteractionType type,
        InteractionRequestData data,
        @JsonProperty("guild_id")
        String guildId,
        @JsonProperty("channel_id")
        String channelId,
        @JsonProperty("guild_member")
        GuildMember guildMember,
        DiscordUser user,
        String token,
        int version,
        DiscordMessage message,
        @JsonProperty("app_permissions")
        String appPermissions,
        String locale,
        @JsonProperty("guild_locale")
        String guildLocale,
        @JsonProperty("entitlement_sku_ids")
        List<String> entitlementSkuIds
) {
}
