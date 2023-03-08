package com.discord.music.component.handlers;

import com.discord.music.component.audio.YouTubeAudioLoadResultHandler;
import com.discord.music.model.CommandHandler;
import com.discord.music.model.Validator;
import com.discord.music.service.VoiceChannelService;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Optional;

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
        Optional<String> validatedURI = extractSongUri(event);

        if (validatedURI.isEmpty()) {
            return event.reply("Link is not recognized as a YouTube or SoundCloud URL.");
        }
        
        Guild guild = event.getInteraction().getGuild().block();

        if (!voiceChannelService.botInAnyChannel(guild)) {
            Member member = event.getInteraction().getMember().get();
            if (!voiceChannelService.userInVoiceChannel(member)) {
                return event.reply("Bot is not connected, and you are not in a voice channel. " +
                        "Please join a channel before queueing songs.").withEphemeral(true);
            }
            voiceChannelService.joinVoiceChannel(member);
        }

        audioPlayerManager
                .loadItem(validatedURI.get(), this.youTubeAudioResultHandler);

        return event.reply("Successfully added " + validatedURI.get() + " to the queue.");
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static Optional<String> extractSongUri(ChatInputInteractionEvent event) {
        String rawURI = event.getOption("song_url")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get();
        for (Validator validator : Validator.values()) {
            Optional<String> validatedURI = validator.getValidUri(rawURI);
            if (validatedURI.isPresent()) {
                return validatedURI;
            }
        }
        return Optional.empty();
    }

}
