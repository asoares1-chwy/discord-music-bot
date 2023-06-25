package com.discord.music.model;

import java.util.regex.Pattern;

public final class InputValidationTools {

    private InputValidationTools() {
    }

    private static final Pattern YT_REGEX = Pattern.compile(
            "^(?:https://)?(?:www.)?youtube.com/watch\\?v=[A-Za-z0-9_-]+", Pattern.CASE_INSENSITIVE);

    private static final Pattern SC_REGEX = Pattern.compile(
            "^(?:(https?)://)?(?:(?:www|m)\\.)?(soundcloud\\.com|snd\\.sc)/(.*)$", Pattern.CASE_INSENSITIVE);

    private static final String YT_SEARCH_PREFIX = "ytsearch:";

    private static final String SC_SEARCH_PREFIX = "scsearch:";

    public static String prependYouTubePrefix(String searchTerm) {
        return YT_SEARCH_PREFIX + searchTerm;
    }

    public static String prependSoundcloudPrefix(String searchTerm) {
        return SC_SEARCH_PREFIX + searchTerm;
    }

    public static boolean isValidYouTubeUrl(String url) {
        return YT_REGEX.matcher(url).find();
    }

    public static boolean isValidSoundcloudUrl(String url) {
        return SC_REGEX.matcher(url).find();
    }
}
