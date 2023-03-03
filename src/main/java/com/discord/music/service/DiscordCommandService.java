package com.discord.music.service;

import com.discord.music.client.DiscordClient;
import com.discord.music.component.SongQueue;
import com.discord.music.model.ApplicationCommand;
import com.discord.music.model.ApplicationCommandRequest;
import com.discord.music.model.InteractionRequest;
import com.discord.music.model.InteractionResponse;
import com.discord.music.model.InteractionResponseData;
import com.discord.music.model.InteractionResponseType;
import com.discord.music.model.MusicBotCommand;
import com.discord.music.model.YouTubeURI;
import com.discord.music.model.queue.ISongQueue;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DiscordCommandService {
    private final Logger logger;
    private final ISongQueue masterSongQueue;
    private final DiscordClient discordClient;

    public DiscordCommandService(Logger logger, DiscordClient discordClient, SongQueue songQueue) {
        this.discordClient = discordClient;
        this.masterSongQueue = songQueue;
        this.logger = logger;
    }

    /**
     * Verifies the given commands are present in the guild. If a command is not present, it will be installed.
     * @param commands the commands to verify
     */
    public void verifyGuildCommands(List<MusicBotCommand> commands) {
        Set<String> commandNames = this.discordClient.getGuildCommands()
                .stream().map(ApplicationCommand::name).collect(Collectors.toCollection(HashSet::new));
        for (MusicBotCommand command : commands) {
            ApplicationCommandRequest request = command.getApplicationCommandRequest();
            if (!commandNames.contains(request.name())) {
                logger.info("missing command with name <{}>. command will be installed.", request.name());
                discordClient.createGuildCommand(request);
            } else {
                logger.debug("command {} is already installed on guild server.", request.name());
            }
        }
    }

    public InteractionResponse handleInteractionCommand(InteractionRequest request) {
        MusicBotCommand command = MusicBotCommand.fromCommandName(request.data().name());
        return switch (command) {
            case PLAY -> {
                YouTubeURI ytUri = YouTubeURI.fromUriString(request.data().options().get(0).value());
                this.masterSongQueue.addSong(ytUri);
                yield new InteractionResponse(InteractionResponseType.CHANNEL_MESSAGE_WITH_SOURCE,
                        new InteractionResponseData("successfully queued " + ytUri.getUri()));
            }
            case SKIP -> {
                this.masterSongQueue.skipSong();
                yield new InteractionResponse(InteractionResponseType.CHANNEL_MESSAGE_WITH_SOURCE,
                        new InteractionResponseData("skipping current song..."));
            }
            case CLEAR_QUEUE -> {
                this.masterSongQueue.clearQueue();
                yield new InteractionResponse(InteractionResponseType.CHANNEL_MESSAGE_WITH_SOURCE,
                        new InteractionResponseData("clearing queue..."));
            }
            case PAUSE -> pauseSong();
            case RESUME -> resumeSong();
        };
    }

    private InteractionResponse pauseSong() {
        // TODO music player should stop, although song remains in queue.
        return new InteractionResponse(InteractionResponseType.CHANNEL_MESSAGE_WITH_SOURCE,
                new InteractionResponseData("pausing song"));
    }

    private InteractionResponse resumeSong() {
        // TODO music player should continue, although song remains in queue.
        return new InteractionResponse(InteractionResponseType.CHANNEL_MESSAGE_WITH_SOURCE,
                new InteractionResponseData("resuming song"));
    }

}
