package com.discord.music.component;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import org.slf4j.Logger;

public class TrackEventLoggingAdapter extends AudioEventAdapter {
    private final Logger logger;

    public TrackEventLoggingAdapter(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        logger.info("track {} ({}) has begun", track.getIdentifier(), track.getInfo().title);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        logger.info("track {} ({}) has ended.", track.getIdentifier(), track.getInfo().title);
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        AudioTrack track = player.getPlayingTrack();
        if (track == null) {
            logger.info("player has been resumed, but there is no track playing.");
            return;
        }
        logger.info("player has been resumed while playing {} ({}).", track.getIdentifier(), track.getInfo().title);
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        AudioTrack track = player.getPlayingTrack();
        if (track == null) {
            logger.info("player has been paused, but there is no track playing.");
            return;
        }
        logger.info("player has been paused while playing {} ({}).", track.getIdentifier(), track.getInfo().title);
    }
}
