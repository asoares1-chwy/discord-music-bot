package com.discord.music.component.handlers;

import com.discord.music.component.SongQueue;
import com.discord.music.component.audio.YouTubeAudioProvider;
import com.discord.music.component.audio.YouTubeAudioResultHandler;
import com.discord.music.model.YouTubeURI;
import com.discord.music.model.queue.ISongQueue;
import com.discord.music.model.CommandHandler;
import com.discord.music.service.VoiceChannelService;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.entity.Guild;
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
    private final AudioPlayerManager audioPlayerManager;
    private final YouTubeAudioResultHandler youTubeAudioResultHandler;
    private final VoiceChannelService voiceChannelService;

    public PlayCommand(SongQueue songQueue,
                       AudioPlayerManager audioPlayerManager,
                       VoiceChannelService voiceChannelService,
                       YouTubeAudioResultHandler youTubeAudioResultHandler) {
        this.songQueue = songQueue;
        this.audioPlayerManager = audioPlayerManager;
        this.voiceChannelService = voiceChannelService;
        this.youTubeAudioResultHandler = youTubeAudioResultHandler;
    }

    @Override
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Mono<Void> executeOnCommand(ChatInputInteractionEvent event) {
        Guild guild = event.getInteraction().getGuild().block();
        Member member = event.getInteraction().getMember().get();
        if (!voiceChannelService.botInMemberChannel(guild, member)) {
            voiceChannelService.joinVoiceChannel(member);
        }
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
        audioPlayerManager.loadItem(youTubeURI.getUri(), youTubeAudioResultHandler);
        return event.reply("Successfully added " + youTubeURI.getUri() + " to the queue.");
    }
}
