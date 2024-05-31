package com.discord.music;

import com.discord.music.component.ChannelTimeoutEventAdapter;
import com.discord.music.component.CommandHandlerRegistrar;
import com.discord.music.component.TrackEventLoggingAdapter;
import com.discord.music.component.audio.SongQueue;
import com.discord.music.service.CommandInstallationService;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final AudioPlayer audioPlayer;
    private final SongQueue songQueue;
    private final TrackEventLoggingAdapter loggingAdapter;
    private final ChannelTimeoutEventAdapter channelTimeoutEventAdapter;
    private final CommandHandlerRegistrar commandHandlerRegistrar;
    private final CommandInstallationService commandInstallationService;

    public ApplicationStartupInitializer(AudioPlayer audioPlayer,
                                         SongQueue songQueue,
                                         TrackEventLoggingAdapter loggingAdapter,
                                         CommandHandlerRegistrar commandHandlerRegistrar,
                                         ChannelTimeoutEventAdapter channelTimeoutEventAdapter,
                                         CommandInstallationService commandInstallationService) {
        this.audioPlayer = audioPlayer;
        this.songQueue = songQueue;
        this.loggingAdapter = loggingAdapter;
        this.commandHandlerRegistrar = commandHandlerRegistrar;
        this.channelTimeoutEventAdapter = channelTimeoutEventAdapter;
        this.commandInstallationService = commandInstallationService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        commandInstallationService.verifyMusicBotCommands();
        commandHandlerRegistrar.registerCommandHandlers();
        this.audioPlayer.addListener(loggingAdapter);
        this.audioPlayer.addListener(songQueue);
        this.audioPlayer.addListener(channelTimeoutEventAdapter);
    }
}
