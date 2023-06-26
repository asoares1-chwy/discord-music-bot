package com.discord.music.service;

import com.discord.music.config.properties.PublicBotProperties;
import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import org.springframework.stereotype.Service;

@Service
public class TextChannelService {
    private final PublicBotProperties publicBotProperties;
    private final GatewayDiscordClient discordClient;

    public TextChannelService(PublicBotProperties publicBotProperties, GatewayDiscordClient gatewayDiscordClient) {
        this.publicBotProperties = publicBotProperties;
        this.discordClient = gatewayDiscordClient;
    }

    public void writeMessageToRequestChannel(String message) {
        discordClient
                .getRestClient()
                .getChannelById(Snowflake.of(publicBotProperties.getRequestChannelId()))
                .createMessage(message)
                .block();
    }
}
