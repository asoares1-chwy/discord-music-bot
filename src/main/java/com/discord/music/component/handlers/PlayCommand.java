package com.discord.music.component.handlers;

import com.discord.music.component.audio.SongQueue;
import com.discord.music.component.audio.YouTubeAudioLoadResultHandler;
import com.discord.music.model.MusicBotException;
import com.discord.music.model.YouTubeURI;
import com.discord.music.model.queue.ISongQueue;
import com.discord.music.model.CommandHandler;
import com.discord.music.service.VoiceChannelService;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class PlayCommand implements CommandHandler<ChatInputInteractionEvent> {
    private final AudioPlayerManager audioPlayerManager;
    private final VoiceChannelService voiceChannelService;
    private final YouTubeAudioLoadResultHandler youTubeAudioResultHandler;

    public PlayCommand(AudioPlayerManager audioPlayerManager,
                       VoiceChannelService voiceChannelService,
                       YouTubeAudioLoadResultHandler youTubeAudioResultHandler) {
        this.audioPlayerManager = audioPlayerManager;
        this.voiceChannelService = voiceChannelService;
        this.youTubeAudioResultHandler = youTubeAudioResultHandler;
    }

    @Override
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Mono<Void> executeOnCommand(ChatInputInteractionEvent event) {
        Guild guild = event.getInteraction().getGuild().block();
        if (!voiceChannelService.botInAnyChannel(guild)) {
            voiceChannelService.joinVoiceChannel(event.getInteraction().getMember().get());
        }
        YouTubeURI youTubeURI;
        try {
            youTubeURI = YouTubeURI.fromUriString(extractSongUri(event));
        } catch (IllegalArgumentException iae) {
            return event.reply("Link is not recognized as a valid YouTube url.");
        }
        audioPlayerManager.loadItem(youTubeURI.getUri(), youTubeAudioResultHandler);
        return event.reply("Successfully added " + youTubeURI.getUri() + " to the queue.");
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static String extractSongUri(ChatInputInteractionEvent event) {
        return event.getOption("song_url")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get();
    }

}
