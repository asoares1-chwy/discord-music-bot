package com.discord.music.model;

import java.util.regex.Pattern;

public class YouTubeURI {
    private final String uri;
    private final String songCode;

    private final static Pattern pattern = Pattern
            .compile("^https?://youtube.com/watch\\?v=[A-Za-z0-9]+(&[A-Za-z0-9=]+$)?");

    private YouTubeURI(String uri, String songCode){
        this.uri = uri;
        this.songCode = songCode;
    }

    public static YouTubeURI fromUriString(String uri) {
        if (!pattern.matcher(uri).matches()) {
            throw new IllegalArgumentException("uri does not match expected pattern of YouTube link.");
        }
        return new YouTubeURI(uri, extractYouTubeVideoCode(uri));
    }

    private static String extractYouTubeVideoCode(String uri) {
        return "";
    }

    public String getUri() {
        return this.uri;
    }

    public String getSongCode() {
        return this.songCode;
    }
}
