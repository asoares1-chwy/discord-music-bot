package com.discord.music.service.d4j;

import com.discord.music.service.d4j.cmd.PlayCommand;
import com.discord.music.service.d4j.cmd.SkipCommand;
import com.discord.music.service.d4j.cmd.UnknownCommand;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CmdService {
    private final Logger logger;

    private final GatewayDiscordClient client;

    private final PlayCommand playCommand;
    private final SkipCommand skipCommand;
    private final UnknownCommand unknownCommand;

    public CmdService(GatewayDiscordClient client,
                      Logger logger,
                      PlayCommand playCommand,
                      SkipCommand skipCommand,
                      UnknownCommand unknownCommand) {
        this.client = client;
        this.logger = logger;
        this.playCommand = playCommand;
        this.skipCommand = skipCommand;
        this.unknownCommand = unknownCommand;

        client.getEventDispatcher().on(ChatInputInteractionEvent.class, event -> {
            logger.info("responding to command {}", event.getCommandName());
            CommandHandler<ChatInputInteractionEvent> cmd = switch (event.getCommandName()) {
                case "play" -> playCommand;
                case "skip" -> skipCommand;
                default -> unknownCommand;
            };
            return cmd.executeOnCommand(event);
        }).subscribe();
    }

    public void registerCommands() {

    }
}
