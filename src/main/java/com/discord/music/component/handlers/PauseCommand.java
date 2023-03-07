package com.discord.music.component.handlers;

import com.discord.music.model.CommandHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PauseCommand implements CommandHandler<ChatInputInteractionEvent> {
    private final AudioPlayer audioPlayer;

    public PauseCommand(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public Mono<Void> executeOnCommand(ChatInputInteractionEvent event) {
        if (!audioPlayer.isPaused() && audioPlayer.getPlayingTrack() != null) {
            audioPlayer.setPaused(true);
            return event
                    .reply("Music Bot is now paused. Resume playing with the /resume command.")
                    .withEphemeral(true);
        }
        if (audioPlayer.getPlayingTrack() == null) {
            return event
                    .reply("uWu, the Music Bot isn't playing anything.")
                    .withEphemeral(true);
        }
        return event
                .reply("uWu, the Music Bot is already paused.")
                .withEphemeral(true);
    }
}
