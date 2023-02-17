package com.discord.music;

import com.discord.music.model.MusicBotCommand;
import com.discord.music.service.DiscordCommandService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ApplicationStartupInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final DiscordCommandService discordService;

    public ApplicationStartupInitializer(DiscordCommandService discordService) {
        this.discordService = discordService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        discordService.verifyGuildCommands(Arrays.asList(MusicBotCommand.values()));
    }
}
