package com.discord.music.component.handlers;

import com.discord.music.model.CommandHandler;
import com.discord.music.model.queue.ISongQueue;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ViewQueueContentsCommand implements CommandHandler<ChatInputInteractionEvent> {
    private final ISongQueue songQueue;

    private static final int PEEK_CAP_LIMIT = 5;

    public ViewQueueContentsCommand(ISongQueue songQueue) {
        this.songQueue = songQueue;
    }

    @Override
    public Mono<Void> executeOnCommand(ChatInputInteractionEvent event) {
        String responseBody = formatViewMessage(songQueue.currentlyPlaying(), songQueue.peekQueueContents());
        return event.reply(responseBody);
    }

    private String formatViewMessage(AudioTrack current, List<AudioTrack> comingUp) {
        StringBuilder builder = new StringBuilder();
        builder.append("__**Currently Playing**__: \uD83C\uDFB5 \n\t")
                .append(current == null ? "<Nothing>" : current.getInfo().title);
        if (comingUp.isEmpty()) {
            return builder.toString();
        }
        builder.append('\n');
        builder.append("__**Coming Up**__: ⏳\n");
        int window = Math.min(PEEK_CAP_LIMIT, comingUp.size());
        for (int i = 0; i < window; i++) {
            builder.append("\t**").append(i + 1).append(".** ").append(comingUp.get(i).getInfo().title).append('\n');
        }
        return builder.toString();
    }
}
