package com.discord.music.service;

import com.discord.music.client.DiscordClient;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class DiscordService {
    private final Logger logger;
    private final DiscordClient discordClient;

    public DiscordService(Logger logger, DiscordClient discordClient) {
        this.discordClient = discordClient;
        this.logger = logger;
    }

    public boolean hasGuildCommand(String command) {
        return this.discordClient.getCommands()
                .stream().anyMatch(x -> x.name().equals(command));
    }
}
