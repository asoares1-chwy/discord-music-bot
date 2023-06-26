package com.discord.music.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("discord.authentication.public")
public class PublicBotProperties {

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getGuildId() {
        return guildId;
    }

    public void setGuildId(Long guildId) {
        this.guildId = guildId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Long getRequestChannelId() {
        return requestChannelId;
    }

    public void setRequestChannelId(Long requestChannelId) {
        this.requestChannelId = requestChannelId;
    }

    private Long appId;
    private Long guildId;
    private String publicKey;
    private Long requestChannelId;
}
