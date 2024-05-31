package com.discord.music.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains utility methods for validating user input, specifically to determine whether the user's search was for a YouTube or Soundcloud link.
 */
public final class InputValidationTools {

    private InputValidationTools() {
    }

    private static final Pattern YT_REGEX = Pattern.compile(
            "^(?:https://)?(?:www.)?youtube.com/watch\\?v=[A-Za-z0-9_-]+", Pattern.CASE_INSENSITIVE);

    private static final Pattern SC_REGEX = Pattern.compile(
            "^(?:(https?)://)?(?:(?:www|m)\\.)?(soundcloud\\.com|snd\\.sc)/(.*)$", Pattern.CASE_INSENSITIVE);

    private static final Pattern YT_VIDEO_ID_REGEX = Pattern.compile("(?<=[?&]v=)([a-zA-Z0-9_-]+)");

    private static final String YT_SEARCH_PREFIX = "ytsearch:";

    private static final String SC_SEARCH_PREFIX = "scsearch:";

    /**
     * Converts a string into a search term recognized by LavaPlayer, specifically for YouTube.
     * @param searchTerm The string to convert to a search term.
     * @return A string representing a LavaPlayer search term.
     */
    public static String prependYouTubePrefix(String searchTerm) {
        return YT_SEARCH_PREFIX + searchTerm;
    }

    /**
     * Converts a string into a search term recognized by LavaPlayer, specifically for Soundcloud.
     * @param searchTerm The string to convert to a search term.
     * @return A string representing a LavaPlayer search term.
     */
    public static String prependSoundcloudPrefix(String searchTerm) {
        return SC_SEARCH_PREFIX + searchTerm;
    }

    /**
     * Determines whether the given string is a valid YouTube video URL.
     * @param url The alleged YouTube URL to validate.
     * @return True if the string is a valid YouTube URI, and false otherwise.
     */
    public static boolean isValidYouTubeUrl(String url) {
        return YT_REGEX.matcher(url).find();
    }

    public static String extractVideoId(String url) {
        Matcher m = YT_VIDEO_ID_REGEX.matcher(url);
        if (m.find()) {
            return m.group();
        }
        throw new MusicBotException("expected valid youtube uri, could not find query parameter 'v'");
    }

    /**
     * Determines whether the given string is a valid Soundcloud audio URL.
     * @param url The alleged Soundcloud URL to validate.
     * @return True if the string is a valid Soundcloud URI, and false otherwise.
     */
    public static boolean isValidSoundcloudUrl(String url) {
        return SC_REGEX.matcher(url).find();
    }
}
