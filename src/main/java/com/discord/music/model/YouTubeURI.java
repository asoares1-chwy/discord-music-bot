package com.discord.music.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubeURI {
    private final String uri;

    private final static Pattern BASE_YOUTUBE_URI_PATTERN = Pattern
            .compile("^(?:https://)?www.youtube.com/watch\\?v=[A-Za-z0-9_-]+", Pattern.CASE_INSENSITIVE);

    private YouTubeURI(String uri) {
        this.uri = uri;
    }

    public static YouTubeURI fromUriString(String uri) {
        Matcher baseUriMatcher = BASE_YOUTUBE_URI_PATTERN.matcher(uri);
        if (!baseUriMatcher.find()) {
            throw new IllegalArgumentException("uri does not match expected pattern of YouTube link.");
        }
        return new YouTubeURI(baseUriMatcher.group());
    }

    public String getUri() {
        return this.uri;
    }
}
