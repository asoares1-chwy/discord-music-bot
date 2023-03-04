package com.discord.music.component.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.GatewayDiscordClient;
import org.springframework.stereotype.Component;

@Component
public class YouTubeAudioResultHandler implements AudioLoadResultHandler {
    private final AudioPlayer audioPlayer;
    private final GatewayDiscordClient discordClient;

    public YouTubeAudioResultHandler(AudioPlayer audioPlayer, GatewayDiscordClient discordClient) {
        this.audioPlayer = audioPlayer;
        this.discordClient = discordClient;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        this.audioPlayer.playTrack(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        // TODO load playlists
    }

    @Override
    public void noMatches() {
        // TODO inform client of missing tracks
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        // TODO inform client of track load failure
    }
}
