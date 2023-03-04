package com.discord.music.component.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.GatewayDiscordClient;
import org.springframework.stereotype.Component;

@Component
public class YouTubeAudioResultHandler implements AudioLoadResultHandler {
    private final GatewayDiscordClient discordClient;

    public YouTubeAudioResultHandler(GatewayDiscordClient discordClient) {
        this.discordClient = discordClient;
    }

    @Override
    public void trackLoaded(AudioTrack track) {

    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {

    }

    @Override
    public void noMatches() {

    }

    @Override
    public void loadFailed(FriendlyException exception) {

    }
}
