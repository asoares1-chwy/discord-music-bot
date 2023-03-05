package com.discord.music.component.audio;

import com.discord.music.model.queue.ISongQueue;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.GatewayDiscordClient;
import org.springframework.stereotype.Component;

@Component
public class YouTubeAudioLoadResultHandler implements AudioLoadResultHandler {
    private final ISongQueue songQueue;
    private final GatewayDiscordClient discordClient;

    public YouTubeAudioLoadResultHandler(GatewayDiscordClient discordClient, ISongQueue songQueue) {
        this.songQueue = songQueue;
        this.discordClient = discordClient;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        this.songQueue.addSong(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        for (AudioTrack track : playlist.getTracks()) {
            this.songQueue.addSong(track);
        }
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
