package com.discord.music.component.handlers;

import com.discord.music.model.CommandHandler;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ResumeCommand implements CommandHandler<ChatInputInteractionEvent> {
    @Override
    public Mono<Void> executeOnCommand(ChatInputInteractionEvent event) {
        return null;
    }
}
