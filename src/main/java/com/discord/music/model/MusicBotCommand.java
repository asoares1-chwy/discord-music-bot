package com.discord.music.model;

import java.util.List;

public enum MusicBotCommand {
    PLAY("play", new ApplicationCommandRequest(
            "play",
            ApplicationCommandType.CHAT_INPUT,
            "Adds a song to the queue.",
            List.of(
                    new ApplicationCommandOption(
                            ApplicationCommandOptionType.STRING,
                            "song_url",
                            "the direct link to the song on YouTube or Spotify",
                            true,
                            List.of()
                    )
            )
    )),
    PAUSE("pause", new ApplicationCommandRequest(
            "pause",
            ApplicationCommandType.CHAT_INPUT,
            "Pause a currently playing song. Has no effect if no song is playing.",
            List.of()
    )),
    RESUME("resume", new ApplicationCommandRequest(
            "resume",
            ApplicationCommandType.CHAT_INPUT,
            "Resumes a paused song. Has no effect if the bot is not paused.",
            List.of()
    )),
    SKIP("skip", new ApplicationCommandRequest(
            "skip",
            ApplicationCommandType.CHAT_INPUT,
            "Skips the currently playing song.",
            List.of()
    )),
    CLEAR_QUEUE("clear", new ApplicationCommandRequest(
            "clear",
            ApplicationCommandType.CHAT_INPUT,
            "Clears the entire music queue.",
            List.of()
    ));

    private final String commandName;
    private final ApplicationCommandRequest applicationCommandRequest;

    MusicBotCommand(String name, ApplicationCommandRequest command) {
        this.commandName = name;
        this.applicationCommandRequest = command;
    }

    public static MusicBotCommand fromCommandName(String commandName) {
        for (MusicBotCommand command : values()) {
            if (command.commandName.equals(commandName)) {
                return command;
            }
        }
        throw new IllegalArgumentException("command " + commandName + " is not known.");
    }

    public String getCommandName() {
        return commandName;
    }

    public ApplicationCommandRequest getApplicationCommandRequest() {
        return this.applicationCommandRequest;
    }
}
