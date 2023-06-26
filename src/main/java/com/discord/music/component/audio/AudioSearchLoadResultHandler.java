package com.discord.music.component.audio;

import com.discord.music.model.ISongQueue;
import com.discord.music.model.MusicBotException;
import com.discord.music.service.TextChannelService;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.springframework.stereotype.Component;

@Component
public class AudioSearchLoadResultHandler implements AudioLoadResultHandler {

    private final TextChannelService textChannelService;
    private final ISongQueue songQueue;

    public AudioSearchLoadResultHandler(ISongQueue songQueue, TextChannelService textChannelService) {
        this.songQueue = songQueue;
        this.textChannelService = textChannelService;
    }

    @Override
    public void trackLoaded(AudioTrack audioTrack) {
        this.respondToLoadEvent(audioTrack);
        this.songQueue.addSong(audioTrack);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        if (playlist.getTracks().isEmpty()) {
            throw new MusicBotException("search term playlist event should return at least 1 result.");
        }
        AudioTrack topResult = playlist.getTracks().get(0);
        this.respondToLoadEvent(topResult);
        this.songQueue.addSong(topResult);
    }

    @Override
    public void noMatches() {

    }

    @Override
    public void loadFailed(FriendlyException exception) {

    }

    private void respondToLoadEvent(AudioTrack audioTrack) {
        textChannelService.writeMessageToRequestChannel(
                "Successfully added search result " + audioTrack.getInfo().uri + " to queue.");
    }
}
