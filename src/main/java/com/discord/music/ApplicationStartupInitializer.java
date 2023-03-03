package com.discord.music;

import com.discord.music.model.MusicBotCommand;
import com.discord.music.service.DiscordCommandService;
import com.discord.music.service.YouTubeDownloadService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ApplicationStartupInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final DiscordCommandService discordService;
    private final YouTubeDownloadService downloadService;

    public ApplicationStartupInitializer(DiscordCommandService discordService, YouTubeDownloadService dl) {
        this.discordService = discordService;
        this.downloadService = dl;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        downloadService.downloadYouTubeVideo("https://www.youtube.com/watch?v=AA-S8CGoFug");
        discordService.verifyGuildCommands(Arrays.asList(MusicBotCommand.values()));
    }
}
