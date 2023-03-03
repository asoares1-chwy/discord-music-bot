package com.discord.music.service.d4j;

import com.discord.music.service.d4j.cmd.PauseCommand;
import com.discord.music.service.d4j.cmd.PlayCommand;
import com.discord.music.service.d4j.cmd.QueueClearCommand;
import com.discord.music.service.d4j.cmd.ResumeCommand;
import com.discord.music.service.d4j.cmd.SkipCommand;
import com.discord.music.service.d4j.cmd.ViewQueueContentsCommand;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.slf4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CmdService implements ApplicationListener<ApplicationReadyEvent> {
    private final Logger logger;

    private final GatewayDiscordClient client;

    private final PlayCommand playCommand;
    private final SkipCommand skipCommand;
    private final PauseCommand pauseCommand;
    private final ResumeCommand resumeCommand;
    private final ViewQueueContentsCommand viewQueueContentsCommand;
    private final QueueClearCommand queueClearCommand;

    public CmdService(GatewayDiscordClient client,
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

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        logger.info("application startup completed, establishing discord command handlers.");
        client.getEventDispatcher().on(ChatInputInteractionEvent.class, event -> {
            logger.info("responding to command {}", event.getCommandName());
            CommandHandler<ChatInputInteractionEvent> cmd = switch (event.getCommandName()) {
                case "play" -> playCommand;
                case "skip" -> skipCommand;
                case "clear" -> queueClearCommand;
                case "pause" -> pauseCommand;
                case "resume" -> resumeCommand;
                case "queue" -> viewQueueContentsCommand;
                default -> (e) -> Mono.empty();
            };
            return cmd.executeOnCommand(event);
        }).subscribe();
        logger.info("command handlers registered.");
    }
}
