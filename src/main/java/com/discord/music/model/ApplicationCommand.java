package com.discord.music.model;

import java.util.List;

public record ApplicationCommand(
        String id,
        ApplicationCommandType type,
        boolean nsfw,
        boolean defaultPermission,
        boolean dmPermission,
        String defaultMemberPermissions,
        List<ApplicationCommandOption> options,
        String name,
        String description,
        String applicationId,
        String guildId,
        String version
) { }
