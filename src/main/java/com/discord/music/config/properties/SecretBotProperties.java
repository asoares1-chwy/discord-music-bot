package com.discord.music.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("discord.authentication.secret")
public class SecretBotProperties {

    public String getDiscordToken() {
        return discordToken;
    }

    public void setDiscordToken(String botToken) {
        this.discordToken = botToken;
    }

    private String discordToken;
}
