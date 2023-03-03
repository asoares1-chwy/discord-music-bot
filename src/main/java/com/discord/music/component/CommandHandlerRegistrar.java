package com.discord.music.component;

import com.discord.music.model.CommandHandler;
import com.discord.music.component.handlers.PauseCommand;
import com.discord.music.component.handlers.PlayCommand;
import com.discord.music.component.handlers.QueueClearCommand;
import com.discord.music.component.handlers.ResumeCommand;
import com.discord.music.component.handlers.SkipCommand;
import com.discord.music.component.handlers.ViewQueueContentsCommand;
import com.discord.music.model.MusicBotCommand;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.slf4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CommandHandlerRegistrar implements ApplicationListener<ApplicationReadyEvent> {
    private final Logger logger;

    private final GatewayDiscordClient client;

    private final PlayCommand playCommand;
    private final SkipCommand skipCommand;
    private final PauseCommand pauseCommand;
    private final ResumeCommand resumeCommand;
    private final ViewQueueContentsCommand viewQueueContentsCommand;
    private final QueueClearCommand queueClearCommand;

    public CommandHandlerRegistrar(GatewayDiscordClient client,
                                   Logger logger,
                                   PlayCommand playCommand,
                                   SkipCommand skipCommand,
                                   PauseCommand pauseCommand,
                                   ResumeCommand resumeCommand,
                                   QueueClearCommand queueClearCommand,
                                   ViewQueueContentsCommand viewQueueContentsCommand) {
        this.client = client;
        this.logger = logger;
        this.playCommand = playCommand;
        this.skipCommand = skipCommand;
        this.queueClearCommand = queueClearCommand;
        this.pauseCommand = pauseCommand;
        this.resumeCommand = resumeCommand;
        this.viewQueueContentsCommand = viewQueueContentsCommand;
    }

    private void registerCommandHandlers() {
        logger.info("application startup completed, establishing discord command handlers.");
        client.getEventDispatcher().on(ChatInputInteractionEvent.class, event -> {
            logger.info("responding to command {}", event.getCommandName());
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
            };
            return cmd.executeOnCommand(event);
        }).subscribe();
        logger.info("command handlers registered.");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        registerCommandHandlers();
    }
}
