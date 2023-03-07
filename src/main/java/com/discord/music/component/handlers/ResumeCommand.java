package com.discord.music.component.handlers;

import com.discord.music.model.CommandHandler;
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
            return event
                    .reply("Music Bot has been resumed. Pause again with the /pause command.")
                    .withEphemeral(true);
        }
        return event
                .reply("uWu, the Music Bot is already playing.")
                .withEphemeral(true);
    }
}
