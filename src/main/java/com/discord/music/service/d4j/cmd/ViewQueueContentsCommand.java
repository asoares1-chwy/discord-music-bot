package com.discord.music.service.d4j.cmd;

import com.discord.music.service.d4j.CommandHandler;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ViewQueueContentsCommand implements CommandHandler<ChatInputInteractionEvent> {
    @Override
    public Mono<Void> executeOnCommand(ChatInputInteractionEvent event) {
        return null;
    }
}
