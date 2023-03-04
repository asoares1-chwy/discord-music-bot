package com.discord.music.service;

import com.discord.music.config.properties.PublicBotProperties;
import com.discord.music.model.MusicBotCommand;
import discord4j.core.GatewayDiscordClient;
import discord4j.discordjson.json.ApplicationCommandData;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommandInstallationService {
    private final GatewayDiscordClient discordClient;
    private final PublicBotProperties pbp;

    public CommandInstallationService(GatewayDiscordClient discordClient, PublicBotProperties pbp) {
        this.discordClient = discordClient;
        this.pbp = pbp;
    }

    private Set<MusicBotCommand> getMissingCommands() {
        Set<String> sm = this.discordClient.getRestClient()
                .getApplicationService()
                .getGuildApplicationCommands(pbp.getAppId(), pbp.getGuildId())
                .map(ApplicationCommandData::name)
                .collect(Collectors.toCollection(HashSet::new))
                .block();
        if (sm == null) {
            throw new RuntimeException("unable to verify guild commands of guild " + pbp.getGuildId());
        }
        return Arrays.stream(MusicBotCommand.values())
                .filter(cmd -> sm.contains(cmd.getLiteralCommand()))
                .collect(Collectors.toSet());
    }

    /**
     * Checks to see which commands are installed on the server.
     * If any commands are missing, they are installed. Otherwise, no action is taken.
     */
    public void verifyMusicBotCommands() {
        Set<MusicBotCommand> commands = getMissingCommands();
        for (MusicBotCommand missing : commands) {
            this.discordClient.getRestClient()
                    .getApplicationService()
                    .createGuildApplicationCommand(pbp.getAppId(), pbp.getGuildId(), missing.getCommandRequest())
                    .subscribe();
        }
    }
}
