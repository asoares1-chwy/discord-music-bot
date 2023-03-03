package com.discord.music.service.d4j;

import discord4j.core.event.domain.Event;
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Lambda compatible interface to process a Discord interaction.
 *
 * @param <T> Any subtype of a Discord4J Event.
 */
public interface CommandHandler<T extends Event> {
    /**
     * Defines a course of action for a specific Discord command.
     *
     * @param event The captured Discord4J Event to process.
     * @return a publisher which will reply to the event.
     */
    Mono<Void> executeOnCommand(T event);
}
