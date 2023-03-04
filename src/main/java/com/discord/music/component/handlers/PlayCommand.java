package com.discord.music.component.handlers;

import com.discord.music.component.SongQueue;
import com.discord.music.component.audio.YouTubeAudioProvider;
import com.discord.music.component.audio.YouTubeAudioResultHandler;
import com.discord.music.model.YouTubeURI;
import com.discord.music.model.queue.ISongQueue;
import com.discord.music.model.CommandHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.spec.VoiceChannelJoinSpec;
import discord4j.voice.VoiceConnection;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class PlayCommand implements CommandHandler<ChatInputInteractionEvent> {
    private final ISongQueue songQueue;
    private final GatewayDiscordClient discordClient;
    private final AudioPlayerManager audioPlayerManager;
    private final YouTubeAudioProvider youTubeAudioProvider;
    private final YouTubeAudioResultHandler youTubeAudioResultHandler;

    public PlayCommand(SongQueue songQueue,
                       AudioPlayerManager audioPlayerManager,
                       GatewayDiscordClient discordClient,
                       YouTubeAudioResultHandler youTubeAudioResultHandler,
                       YouTubeAudioProvider youTubeAudioProvider) {
        this.songQueue = songQueue;
        this.discordClient = discordClient;
        this.audioPlayerManager = audioPlayerManager;
        this.youTubeAudioProvider = youTubeAudioProvider;
        this.youTubeAudioResultHandler = youTubeAudioResultHandler;
    }

    @Override
    public Mono<Void> executeOnCommand(ChatInputInteractionEvent event) {
        final Member member = event.getInteraction().getMember().orElse(null);
        if (member != null) {
            final VoiceState voiceState = member.getVoiceState().block();
            if (voiceState != null) {
                final VoiceChannel channel = voiceState.getChannel().block();
                if (channel != null) {
                    // join returns a VoiceConnection which would be required if we were
                    // adding disconnection features, but for now we are just ignoring it.
                    channel.join(spec -> spec.setProvider(youTubeAudioProvider)).block();
                }
            }
        }
//        @SuppressWarnings("OptionalGetWithoutIsPresent")
//        String uri = event.getOption("song_url")
//                .flatMap(ApplicationCommandInteractionOption::getValue)
//                .map(ApplicationCommandInteractionOptionValue::asString)
//                .get();
//        YouTubeURI youTubeURI;
//        try {
//            youTubeURI = YouTubeURI.fromUriString(uri);
//        } catch (IllegalArgumentException iae) {
//            return event.reply("Link is not recognized as a valid YouTube url.");
//        }
//        songQueue.addSong(youTubeURI);
//        audioPlayerManager.loadItem(youTubeURI.getUri(), youTubeAudioResultHandler);
//        return event.reply("Successfully added " + youTubeURI.getUri() + " to the queue.");
        return event.reply("hello");
    }

    private void joinVoiceChannel(Optional<Member> member) {
        if (member.isEmpty()) {
            return;
        }
        VoiceState voiceState = member.get().getVoiceState().block();
        if (voiceState == null) {
            return;
        }
        VoiceChannel channel = voiceState.getChannel().block();
        if (channel == null) {
            return;
        }
        VoiceChannelJoinSpec spec = VoiceChannelJoinSpec.builder()
                .provider(youTubeAudioProvider)
                //.timeout(Duration.ofMinutes(1))
                .build();
        channel.join(spec).block();
    }

    private void playTrack() {

    }
}
