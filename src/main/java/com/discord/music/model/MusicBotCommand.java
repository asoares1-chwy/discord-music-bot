package com.discord.music.model;

import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;

public enum MusicBotCommand {
    PLAY("play", ApplicationCommandRequest.builder()
            .name("play")
            .description("Adds a song to the queue.")
            .addOption(ApplicationCommandOptionData.builder()
                    .name("search_term")
                    .description("The YouTube/Soundcloud URL, or a track to search for.")
                    .type(ApplicationCommandOption.Type.STRING.getValue())
                    .required(true)
                    .build()
            )
            .build()),
    PAUSE("pause", ApplicationCommandRequest.builder()
            .name("pause")
            .description("Pauses the Music Bot. No effect if the bot is not playing.")
            .build()),
    RESUME("resume", ApplicationCommandRequest.builder()
            .name("resume")
            .description("Resumes the Music Bot. No effect if the bot is not playing.")
            .build()),
    SKIP("skip", ApplicationCommandRequest.builder()
            .name("skip")
            .description("Skips the currently playing song.")
            .build()),
    CLEAR("clear", ApplicationCommandRequest.builder()
            .name("clear")
            .description("Clears the contents of the queue. Stops the currently playing song.")
            .build()),
    VIEW_QUEUE("queue", ApplicationCommandRequest.builder()
            .name("queue")
            .description("See what's playing, and what's coming up next!")
            .build());

    private final String literalCommand;
    private final ApplicationCommandRequest commandRequest;

    MusicBotCommand(String literalCommand, ApplicationCommandRequest commandRequest) {
        this.literalCommand = literalCommand;
        this.commandRequest = commandRequest;
    }

    public static MusicBotCommand fromLiteralCommand(String command) {
        for (MusicBotCommand mbc : values()) {
            if (mbc.literalCommand.equals(command)) {
                return mbc;
            }
        }
        return null;
    }

    public String getLiteralCommand() {
        return literalCommand;
    }

    public ApplicationCommandRequest getCommandRequest() {
        return commandRequest;
    }
}
