package com.discord.music.service.d4j.cmd;

import com.discord.music.component.SongQueue;
import com.discord.music.model.YouTubeURI;
import com.discord.music.model.queue.ISongQueue;
import com.discord.music.service.d4j.CommandHandler;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PlayCommand implements CommandHandler<ChatInputInteractionEvent> {
    private final ISongQueue songQueue;

    public PlayCommand(SongQueue songQueue) {
        this.songQueue = songQueue;
    }

    @Override
    public Mono<Void> executeOnCommand(ChatInputInteractionEvent event) {
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        String uri = event.getOption("song_url")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get();
        YouTubeURI youTubeURI;
        try {
            youTubeURI = YouTubeURI.fromUriString(uri);
        } catch (IllegalArgumentException iae) {
            return event.reply("Link is not recognized as a valid YouTube url.");
        }
        songQueue.addSong(youTubeURI);
        return event.reply("Successfully added " + youTubeURI.getUri() + " to the queue.");
    }
}
