package com.discord.music.model;

public enum MusicBotCommand {
    PLAY("play"),
    PAUSE("pause"),
    RESUME("resume"),
    SKIP("skip"),
    CLEAR("clear"),
    VIEW_QUEUE("queue");

    private final String literalCommand;

    MusicBotCommand(String literalCommand) {
        this.literalCommand = literalCommand;
    }

    public static MusicBotCommand fromLiteralCommand(String command) {
        for (MusicBotCommand mbc : values()) {
            if (mbc.literalCommand.equals(command)) {
                return mbc;
            }
        }
        return null;
    }
}
