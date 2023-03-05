package com.discord.music.component.handlers;

import com.discord.music.component.audio.SongQueue;
import com.discord.music.model.queue.ISongQueue;
import com.discord.music.model.CommandHandler;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SkipCommand implements CommandHandler<ChatInputInteractionEvent> {
    private final ISongQueue songQueue;

    public SkipCommand(SongQueue songQueue) {
        this.songQueue = songQueue;
    }

    @Override
    public Mono<Void> executeOnCommand(ChatInputInteractionEvent event) {
        if (songQueue.skipSong()) {
            return event.reply("Skipping currently playing song.");
        }
        return event.reply("Queue is empty, nothing to skip.");
    }
}
