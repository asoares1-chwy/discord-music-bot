package com.discord.music.component.handlers;

import com.discord.music.component.audio.AudioSearchLoadResultHandler;
import com.discord.music.component.audio.DirectUrlAudioLoadResultHandler;
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

import static com.discord.music.model.InputValidationTools.*;

@Component
public class PlayCommand implements CommandHandler<ChatInputInteractionEvent> {
    private final AudioPlayerManager audioPlayerManager;
    private final VoiceChannelService voiceChannelService;
    private final DirectUrlAudioLoadResultHandler directUrlAudioLoadResultHandler;
    private final AudioSearchLoadResultHandler audioSearchLoadResultHandler;

    public PlayCommand(AudioPlayerManager audioPlayerManager,
                       VoiceChannelService voiceChannelService,
                       AudioSearchLoadResultHandler audioSearchLoadResultHandler,
                       DirectUrlAudioLoadResultHandler directUrlAudioLoadResultHandler) {
        this.audioPlayerManager = audioPlayerManager;
        this.voiceChannelService = voiceChannelService;
        this.audioSearchLoadResultHandler = audioSearchLoadResultHandler;
        this.directUrlAudioLoadResultHandler = directUrlAudioLoadResultHandler;
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

        if (isValidYouTubeUrl(requestTerm)) {
            String videoId = extractVideoId(requestTerm);
            audioPlayerManager.loadItem(videoId, this.directUrlAudioLoadResultHandler);
            return event.reply("Successfully added " + requestTerm + " to the queue.");
        }

        if (isValidSoundcloudUrl(requestTerm)) {
            audioPlayerManager.loadItem(requestTerm, this.directUrlAudioLoadResultHandler);
            return event.reply("Successfully added " + requestTerm + " to the queue.");
        }

        audioPlayerManager.loadItem(prependYouTubePrefix(requestTerm), this.audioSearchLoadResultHandler);
        return event.reply("Searching for '" + requestTerm + "' on YouTube.").withEphemeral(true);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static String extractRequestParameter(ChatInputInteractionEvent event) {
        return event.getOption("search_term")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get();
    }

}
