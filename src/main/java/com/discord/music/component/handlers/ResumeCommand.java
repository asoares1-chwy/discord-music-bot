package com.discord.music.component.handlers;

import com.discord.music.model.CommandHandler;
import com.discord.music.model.queue.ISongQueue;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ResumeCommand implements CommandHandler<ChatInputInteractionEvent> {
    private final AudioPlayer audioPlayer;

    public ResumeCommand(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public Mono<Void> executeOnCommand(ChatInputInteractionEvent event) {
        if (audioPlayer.isPaused()) {
            audioPlayer.setPaused(false);
        }
        return event.reply();
    }
}
