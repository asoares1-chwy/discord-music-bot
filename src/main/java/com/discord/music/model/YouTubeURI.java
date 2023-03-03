package com.discord.music.model;

import java.util.regex.Pattern;

public class YouTubeURI {
    private final String uri;

    private final static Pattern pattern = Pattern
            .compile("https://www.youtube.com/watch\\?v=[A-Za-z0-9]+", Pattern.CASE_INSENSITIVE);

    private YouTubeURI(String uri) {
        this.uri = uri;
    }

    public static YouTubeURI fromUriString(String uri) {
        if (!pattern.matcher(uri).matches()) {
            throw new IllegalArgumentException("uri does not match expected pattern of YouTube link.");
        }
        return new YouTubeURI(uri);
    }

    public String getUri() {
        return this.uri;
    }
}
