package com.discord.music.service.d4j;

import discord4j.core.event.domain.Event;
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommandHandler<T extends Event> {
    Mono<Void> executeOnCommand(T event);
}
