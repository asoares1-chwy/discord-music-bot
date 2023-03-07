package com.discord.music.component;

import com.discord.music.model.queue.ISongQueue;
import com.discord.music.service.VoiceChannelService;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Class responsible for timing out the bot after a period of inactivity.
 * When a song ends, a job is scheduled to remove the bot after the specified timeout.
 * When a song begins, this job is removed from the queue.
 */
@Component
public class ChannelTimeoutEventAdapter extends AudioEventAdapter {
    private final Logger logger;
    private final ISongQueue songQueue;
    private final ThreadPoolTaskScheduler scheduler;
    private final VoiceChannelService voiceChannelService;
    private final int inactivityTimeoutSeconds;

    public ChannelTimeoutEventAdapter(Logger logger,
                                      ISongQueue songQueue,
                                      @Qualifier("ChannelTimeout") ThreadPoolTaskScheduler scheduler,
                                      VoiceChannelService voiceChannelService,
                                      @Value("${discord.music-bot.inactivity-timeout-seconds}") int its) {
        this.logger = logger;
        this.scheduler = scheduler;
        this.songQueue = songQueue;
        this.inactivityTimeoutSeconds = its;
        this.voiceChannelService = voiceChannelService;
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        scheduler.schedule(() -> {
                    if (!songQueue.isActiveState()) {
                        logger.info("no audio activity detected in last {} seconds, leaving voice channel.",
                                inactivityTimeoutSeconds);
                        voiceChannelService.leaveVoiceChannel();
                    }
                },
                Instant.now().plusSeconds(inactivityTimeoutSeconds));
    }
}
