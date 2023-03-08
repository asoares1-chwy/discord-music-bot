package com.discord.music.model;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Validator {
    YOUTUBE(Pattern.compile("^(?:https://)?(?:www.)?youtube.com/watch\\?v=[A-Za-z0-9_-]+", Pattern.CASE_INSENSITIVE)),
    SOUNDCLOUD(Pattern.compile("^(?:https://)?(?:www.)?soundcloud.com/[A-Za-z0-9_-]+/[A-Za-z0-9_-]+$"));

    private final Pattern regExpr;

    Validator(Pattern regExpr) {
        this.regExpr = regExpr;
    }

    public Optional<String> getValidUri(String uri) {
        Matcher baseUriMatcher = this.regExpr.matcher(uri);
        if (!baseUriMatcher.find()) {
            return Optional.empty();
        }
        return Optional.of(baseUriMatcher.group());
    }

}
