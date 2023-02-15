package com.discord.music.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ApplicationCommand(
        String id,
        ApplicationCommandType type,
        boolean nsfw,
        @JsonProperty("default_permission")
        boolean defaultPermission,
        @JsonProperty("dm_permission")
        boolean dmPermission,
        @JsonProperty("default_member_permissions")
        String defaultMemberPermissions,
        List<ApplicationCommandOption> options,
        String name,
        String description,
        @JsonProperty("application_id")
        String applicationId,
        @JsonProperty("guild_id")
        String guildId,
        String version
) { }
