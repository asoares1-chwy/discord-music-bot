package com.discord.music.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("discord.authentication.public")
public class PublicBotProperties {

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    private String appId;
    private String guildId;
    private String publicKey;
}
