package com.discord.music.component.audio;

import com.discord.music.model.MusicBotException;
import com.discord.music.model.queue.ISongQueue;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Component
public class SongQueue extends AudioEventAdapter implements ISongQueue {
    private final Logger logger;
    private final BlockingDeque<AudioTrack> queue;
    private final AudioPlayer audioPlayer;

    public SongQueue(Logger logger, AudioPlayer audioPlayer) {
        this.logger = logger;
        this.audioPlayer = audioPlayer;
        this.queue = new LinkedBlockingDeque<>();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            this.skipSong();
        }
    }

    @Override
    public void addSong(AudioTrack track) {
        if (audioPlayer.startTrack(track, true)) {
            return;
        }
        try {
            this.queue.putLast(track);
        } catch (InterruptedException e) {
            throw new MusicBotException("could not add track " + track.getIdentifier() + " to queue.");
        }
    }

    @Override
    @Nullable
    public AudioTrack currentlyPlaying() {
        return this.audioPlayer.getPlayingTrack();
    }

    @Override
    public boolean skipSong() {
        if (!this.isActiveState()) {
            return false;
        }
        if (queue.isEmpty()) {
            if (audioPlayer.getPlayingTrack() == null) {
                return false;
            }
            audioPlayer.stopTrack();
            return true;
        }
        if (audioPlayer.startTrack(queue.getFirst(), false)) {
            queue.pollFirst();
            return true;
        }
        logger.warn("failed to skip to following track: {}. it was not removed from the queue.",
                audioPlayer.getPlayingTrack().getIdentifier());
        return false;
    }

    @Override
    public boolean clearQueue() {
        if (queue.isEmpty()) {
            if (audioPlayer.getPlayingTrack() != null) {
                audioPlayer.stopTrack();
                return true;
            }
            return false;
        }
        queue.clear();
        audioPlayer.stopTrack();
        return true;
    }

    @Override
    public List<AudioTrack> peekQueueContents() {
        return this.queue.stream().toList();
    }

    @Override
    public boolean isActiveState() {
        return this.currentlyPlaying() != null || !this.queue.isEmpty();
    }

}
