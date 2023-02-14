package com.discord.music.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("discord.authentication.secret")
public class SecretBotProperties {

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    private String botToken;
}
