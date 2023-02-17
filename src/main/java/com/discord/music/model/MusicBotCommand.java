package com.discord.music.model;

import java.util.List;

public enum MusicBotCommand {
    PLAY(new ApplicationCommandRequest(
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
    PAUSE(new ApplicationCommandRequest(
            "pause",
            ApplicationCommandType.CHAT_INPUT,
            "Pause a currently playing song. Has no effect if no song is playing.",
            List.of()
    )),
    RESUME(new ApplicationCommandRequest(
            "resume",
            ApplicationCommandType.CHAT_INPUT,
            "Resumes a paused song. Has no effect if the bot is not paused.",
            List.of()
    )),
    SKIP(new ApplicationCommandRequest(
            "skip",
            ApplicationCommandType.CHAT_INPUT,
            "Skips the currently playing song.",
            List.of()
    )),
    CLEAR_QUEUE(new ApplicationCommandRequest(
            "clear",
            ApplicationCommandType.CHAT_INPUT,
            "Clears the entire music queue.",
            List.of()
    ));

    private final ApplicationCommandRequest applicationCommandRequest;

    MusicBotCommand(ApplicationCommandRequest command) {
        this.applicationCommandRequest = command;
    }

    public ApplicationCommandRequest getApplicationCommandRequest() {
        return this.applicationCommandRequest;
    }
}
