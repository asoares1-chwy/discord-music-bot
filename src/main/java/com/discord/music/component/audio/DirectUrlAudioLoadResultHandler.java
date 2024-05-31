package com.discord.music.component.audio;

import com.discord.music.model.ISongQueue;
import com.discord.music.service.TextChannelService;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.springframework.stereotype.Component;

@Component
public class DirectUrlAudioLoadResultHandler implements AudioLoadResultHandler {
    private final ISongQueue songQueue;
    private final TextChannelService textChannelService;

    public DirectUrlAudioLoadResultHandler(ISongQueue songQueue, TextChannelService textChannelService) {
        this.songQueue = songQueue;
        this.textChannelService = textChannelService;
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
        textChannelService.writeMessageToRequestChannel(
                "The request was a valid YouTube or Soundcloud link, but did not resolve to any content.");
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        textChannelService.writeMessageToRequestChannel("Oh no! We couldn't load your track request. Why? "
                + exception.getMessage());
    }
}
