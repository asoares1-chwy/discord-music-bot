package com.discord.music.component.handlers;

import com.discord.music.component.audio.SongQueue;
import com.discord.music.model.queue.ISongQueue;
import com.discord.music.model.CommandHandler;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class QueueClearCommand implements CommandHandler<ChatInputInteractionEvent> {
    private final ISongQueue songQueue;

    public QueueClearCommand(SongQueue songQueue) {
        this.songQueue = songQueue;
    }

    @Override
    public Mono<Void> executeOnCommand(ChatInputInteractionEvent event) {
        if (songQueue.clearQueue()) {
            return event.reply("Music Bot queue has been cleared.");
        }
        return event
                .reply("Music Bot queue is empty, nothing to clear.")
                .withEphemeral(true);
    }
}
