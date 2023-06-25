package com.discord.music.component.handlers;

import com.discord.music.component.audio.AudioSearchLoadResultHandler;
import com.discord.music.component.audio.DirectUrlAudioLoadResultHandler;
import com.discord.music.model.CommandHandler;
import com.discord.music.model.ISongQueue;
import com.discord.music.model.InputValidationTools;
import com.discord.music.service.VoiceChannelService;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PlayCommand implements CommandHandler<ChatInputInteractionEvent> {
    private final AudioPlayerManager audioPlayerManager;
    private final ISongQueue songQueue;
    private final VoiceChannelService voiceChannelService;
    private final DirectUrlAudioLoadResultHandler directUrlAudioLoadResultHandler;
    private final AudioSearchLoadResultHandler audioSearchLoadResultHandler;

    private final static String YT_SEARCH_PREFIX = "ytsearch:";

    public PlayCommand(AudioPlayerManager audioPlayerManager,
                       VoiceChannelService voiceChannelService,
                       ISongQueue queue,
                       AudioSearchLoadResultHandler audioSearchLoadResultHandler,
                       DirectUrlAudioLoadResultHandler directUrlAudioLoadResultHandler) {
        this.audioPlayerManager = audioPlayerManager;
        this.voiceChannelService = voiceChannelService;
        this.audioSearchLoadResultHandler = audioSearchLoadResultHandler;
        this.directUrlAudioLoadResultHandler = directUrlAudioLoadResultHandler;
        this.songQueue = queue;
    }

    @Override
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Mono<Void> executeOnCommand(ChatInputInteractionEvent event) {
        Guild guild = event.getInteraction().getGuild().block();

        if (!voiceChannelService.botInAnyChannel(guild)) {
            Member member = event.getInteraction().getMember().get();
            if (!voiceChannelService.userInVoiceChannel(member)) {
                return event.reply("Bot is not connected, and you are not in a voice channel. " +
                        "Please join a channel before queueing songs.").withEphemeral(true);
            }
            voiceChannelService.joinVoiceChannel(member);
        }

        String requestTerm = extractRequestParameter(event);

        if (InputValidationTools.isValidYouTubeUrl(requestTerm) || InputValidationTools.isValidSoundcloudUrl(requestTerm)) {
            audioPlayerManager.loadItem(requestTerm, this.directUrlAudioLoadResultHandler);
        } else {
            audioPlayerManager.loadItem(
                    InputValidationTools.prependYouTubePrefix(requestTerm), this.audioSearchLoadResultHandler);
        }

        return event.reply("Successfully added " + requestTerm + " to the queue.");
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static String extractRequestParameter(ChatInputInteractionEvent event) {
        return event.getOption("song_url")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get();
    }


}
