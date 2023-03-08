package com.discord.music.component.handlers;

import com.discord.music.component.audio.SongQueue;
import com.discord.music.model.ISongQueue;
import com.discord.music.model.CommandHandler;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
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
        AudioTrack currentTrack = songQueue.currentlyPlaying();
        if (songQueue.skipSong()) {
            return event.reply("Skipped <" + currentTrack.getInfo().title + ">");
        }
        return event
                .reply("No song is playing, and the queue is empty.")
                .withEphemeral(true);
    }
}
