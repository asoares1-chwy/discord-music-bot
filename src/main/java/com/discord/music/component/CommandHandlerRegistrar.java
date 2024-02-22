package com.discord.music.component;

import com.discord.music.component.handlers.*;
import com.discord.music.model.CommandHandler;
import com.discord.music.model.MusicBotCommand;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CommandHandlerRegistrar {
    private final Logger logger;

    private final GatewayDiscordClient client;

    private final PlayCommand playCommand;
    private final SkipCommand skipCommand;
    private final PauseCommand pauseCommand;
    private final ResumeCommand resumeCommand;
    private final LeaveCommand leaveCommand;
    private final ViewQueueContentsCommand viewQueueContentsCommand;
    private final QueueClearCommand queueClearCommand;

    public CommandHandlerRegistrar(GatewayDiscordClient client,
                                   Logger logger,
                                   PlayCommand playCommand,
                                   SkipCommand skipCommand,
                                   PauseCommand pauseCommand,
                                   ResumeCommand resumeCommand,
                                   QueueClearCommand queueClearCommand,
                                   LeaveCommand leaveCommand,
                                   ViewQueueContentsCommand viewQueueContentsCommand) {
        this.client = client;
        this.logger = logger;
        this.playCommand = playCommand;
        this.skipCommand = skipCommand;
        this.queueClearCommand = queueClearCommand;
        this.pauseCommand = pauseCommand;
        this.resumeCommand = resumeCommand;
        this.leaveCommand = leaveCommand;
        this.viewQueueContentsCommand = viewQueueContentsCommand;
    }

    /**
     * Assigns a handler to each individual command.
     * Each handler will be invoked only when user submits that specific command.
     */
    public void registerCommandHandlers() {
        logger.info("application startup completed, establishing discord command handlers.");
        client.getEventDispatcher().on(ChatInputInteractionEvent.class, event -> {
            logger.info("responding to command '{}'", event.getCommandName());
            MusicBotCommand command = MusicBotCommand.fromLiteralCommand(event.getCommandName());
            if (command == null) {
                return Mono.empty();
            }
            CommandHandler<ChatInputInteractionEvent> cmd = switch (command) {
                case PLAY -> playCommand;
                case SKIP -> skipCommand;
                case CLEAR -> queueClearCommand;
                case PAUSE -> pauseCommand;
                case RESUME -> resumeCommand;
                case VIEW_QUEUE -> viewQueueContentsCommand;
                case LEAVE -> leaveCommand;
            };
            return cmd.executeOnCommand(event);
        }).subscribe();
    }
}
