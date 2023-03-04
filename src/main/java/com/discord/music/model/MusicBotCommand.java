package com.discord.music.model;

import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;

public enum MusicBotCommand {
    PLAY("play", ApplicationCommandRequest.builder()
            .name("play")
            .description("Play a song")
            .addOption(ApplicationCommandOptionData.builder()
                    .name("song_url")
                    .description("the youtube uri of the song")
                    .type(ApplicationCommandOption.Type.STRING.getValue())
                    .required(true)
                    .build()
            )
            .build()),
    PAUSE("pause", ApplicationCommandRequest.builder()
            .name("pause")
            .description("pause a song")
            .build()),
    RESUME("resume", ApplicationCommandRequest.builder()
            .name("resume")
            .description("resume a song")
            .build()),
    SKIP("skip", ApplicationCommandRequest.builder()
            .name("skip")
            .description("skip currently playing song")
            .build()),
    CLEAR("clear", ApplicationCommandRequest.builder()
            .name("clear")
            .description("clear the queue")
            .build()),
    VIEW_QUEUE("queue", ApplicationCommandRequest.builder()
            .name("queue")
            .description("view the contents of the queue")
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
