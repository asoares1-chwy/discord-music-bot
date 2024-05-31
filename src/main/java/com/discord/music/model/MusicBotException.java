package com.discord.music.model;

/**
 * Wrapper class for a RuntimeException.
 * Used to differentiate explicitly thrown Exceptions from this application, and generic RuntimeExceptions.
 */
public class MusicBotException extends RuntimeException {
    public MusicBotException(String message) {
        super(message);
    }

    public MusicBotException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
