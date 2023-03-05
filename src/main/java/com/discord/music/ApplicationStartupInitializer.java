package com.discord.music;

import com.discord.music.component.CommandHandlerRegistrar;
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
    private final CommandHandlerRegistrar commandHandlerRegistrar;
    private final CommandInstallationService commandInstallationService;

    public ApplicationStartupInitializer(AudioPlayer audioPlayer,
                                         SongQueue songQueue,
                                         CommandHandlerRegistrar commandHandlerRegistrar,
                                         CommandInstallationService commandInstallationService) {
        this.audioPlayer = audioPlayer;
        this.songQueue = songQueue;
        this.commandHandlerRegistrar = commandHandlerRegistrar;
        this.commandInstallationService = commandInstallationService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        commandInstallationService.verifyMusicBotCommands();
        commandHandlerRegistrar.registerCommandHandlers();
        this.audioPlayer.addListener(songQueue);
    }
}
