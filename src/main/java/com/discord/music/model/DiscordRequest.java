package com.discord.music.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DiscordRequest {
    private String applicationId;
    private String id;
    private String token;
    private InteractionType type;
    private DiscordUser user;
    private int version;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public InteractionType getType() {
        return type;
    }

    public void setType(InteractionType type) {
        this.type = type;
    }

    public DiscordUser getUser() {
        return user;
    }

    public void setUser(DiscordUser user) {
        this.user = user;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "DiscordRequest{" +
                "applicationId='" + applicationId + '\'' +
                ", id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", type=" + type +
                ", user=" + user +
                ", version=" + version +
                '}';
    }
}
