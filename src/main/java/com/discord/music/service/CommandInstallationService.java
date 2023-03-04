package com.discord.music.service;

import com.discord.music.config.properties.PublicBotProperties;
import com.discord.music.model.MusicBotCommand;
import discord4j.core.GatewayDiscordClient;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class CommandInstallationService implements ApplicationListener<ApplicationReadyEvent> {
    private final GatewayDiscordClient discordClient;
    private final PublicBotProperties pbp;

    public CommandInstallationService(GatewayDiscordClient discordClient, PublicBotProperties pbp) {
        this.discordClient = discordClient;
        this.pbp = pbp;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        for (MusicBotCommand cmd : MusicBotCommand.values()) {
            this.discordClient.getRestClient().getApplicationService()
                    .createGuildApplicationCommand(
                            Long.parseLong(pbp.getAppId()),
                            Long.parseLong(pbp.getGuildId()),
                            cmd.getCommandRequest())
                    .subscribe();
        }
    }
}
