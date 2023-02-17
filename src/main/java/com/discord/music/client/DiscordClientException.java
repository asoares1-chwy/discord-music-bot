package com.discord.music.client;

import okhttp3.HttpUrl;

public class DiscordClientException extends RuntimeException {
    public DiscordClientException(String message, Throwable reason) {
        super(message, reason);
    }

    public DiscordClientException(String method, HttpUrl url, int status, String message) {
        super(String.format("method: <%s>  url: <%s> status: <%d> message: <%s>",
                method, url, status, message));
    }
}
