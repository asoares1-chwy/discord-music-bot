package com.discord.music.service;

import com.discord.music.client.DiscordClient;
import com.discord.music.model.ApplicationCommand;
import com.discord.music.model.ApplicationCommandRequest;
import com.discord.music.model.MusicBotCommand;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DiscordService {
    private final Logger logger;
    private final DiscordClient discordClient;

    public DiscordService(Logger logger, DiscordClient discordClient) {
        this.discordClient = discordClient;
        this.logger = logger;
    }

    /**
     * Verifies the given commands are present in the guild. If a command is not present, it will be installed.
     * @param commands the commands to verify
     */
    public void verifyGuildCommands(List<MusicBotCommand> commands) {
        Set<String> commandNames = this.discordClient.getGuildCommands()
                .stream().map(ApplicationCommand::name).collect(Collectors.toCollection(HashSet::new));
        for (MusicBotCommand command : commands) {
            ApplicationCommandRequest request = command.getApplicationCommandRequest();
            if (!commandNames.contains(request.name())) {
                logger.info("missing command with name {}. command will be installed.", request.name());
                discordClient.createGuildCommand(request);
            } else {
                logger.debug("command {} is already installed on guild server.", request.name());
            }
        }
    }
}
