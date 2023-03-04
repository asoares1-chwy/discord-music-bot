package com.discord.music;

import com.discord.music.component.CommandHandlerRegistrar;
import com.discord.music.service.CommandInstallationService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final CommandHandlerRegistrar commandHandlerRegistrar;
    private final CommandInstallationService commandInstallationService;

    public ApplicationStartupInitializer(CommandHandlerRegistrar commandHandlerRegistrar,
                                         CommandInstallationService commandInstallationService) {
        this.commandHandlerRegistrar = commandHandlerRegistrar;
        this.commandInstallationService = commandInstallationService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        commandInstallationService.verifyMusicBotCommands();
        commandHandlerRegistrar.registerCommandHandlers();
    }
}
