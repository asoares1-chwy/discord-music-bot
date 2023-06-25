package com.discord.music.component.audio;

import com.discord.music.model.ISongQueue;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.GatewayDiscordClient;
import org.springframework.stereotype.Component;

@Component
public class AudioSearchLoadResultHandler implements AudioLoadResultHandler {

    private final ISongQueue songQueue;

    public AudioSearchLoadResultHandler(ISongQueue songQueue) {
        this.songQueue = songQueue;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        this.songQueue.addSong(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        this.songQueue.addSong(playlist.getTracks().get(0));
    }

    @Override
    public void noMatches() {

    }

    @Override
    public void loadFailed(FriendlyException exception) {

    }
}
