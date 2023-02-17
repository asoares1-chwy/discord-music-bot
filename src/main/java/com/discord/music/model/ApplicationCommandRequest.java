package com.discord.music.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record ApplicationCommandRequest(
        String name,
        ApplicationCommandType type,
        String description,
        List<ApplicationCommandOption> options,
        @JsonProperty("name_localizations")
        Map<String, String> nameLocalizations,
        @JsonProperty("description_localizations")
        Map<String, String> descriptionLocalizations,
        @JsonProperty("default_member_permissions")
        String defaultMemberPermissions,
        @JsonProperty("default_permission")
        boolean defaultPermission,
        boolean nsfw
) {
    public ApplicationCommandRequest(
            String name,
            ApplicationCommandType type,
            String description,
            List<ApplicationCommandOption> options
    ) {
        this(name, type, description, options, Map.of(), Map.of(), null, false, false);
    }
}
