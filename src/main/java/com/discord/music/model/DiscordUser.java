package com.discord.music.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DiscordUser {
    private String avatar;
    private String avatarDecoration;
    private String discriminator;
    private String displayName;
    private String id;
    private int publicFlags;
    private String username;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarDecoration() {
        return avatarDecoration;
    }

    public void setAvatarDecoration(String avatarDecoration) {
        this.avatarDecoration = avatarDecoration;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPublicFlags() {
        return publicFlags;
    }

    public void setPublicFlags(int publicFlags) {
        this.publicFlags = publicFlags;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "DiscordUser{" +
                "avatar='" + avatar + '\'' +
                ", avatarDecoration='" + avatarDecoration + '\'' +
                ", discriminator='" + discriminator + '\'' +
                ", displayName='" + displayName + '\'' +
                ", id='" + id + '\'' +
                ", publicFlags=" + publicFlags +
                ", username='" + username + '\'' +
                '}';
    }
}
