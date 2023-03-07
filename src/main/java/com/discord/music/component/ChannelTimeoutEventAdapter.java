package com.discord.music.component;

import com.discord.music.model.MusicBotException;
import com.discord.music.service.VoiceChannelService;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Class responsible for timing out the bot after a period of inactivity.
 * When a song ends, a job is scheduled to remove the bot after the specified timeout.
 * When a song begins, this job is removed from the queue.
 */
@Component
public class ChannelTimeoutEventAdapter extends AudioEventAdapter {
    private final Logger logger;
    private final int inactivityTimeoutSeconds;
    private final VoiceChannelService voiceChannelService;
    private final ThreadPoolTaskScheduler scheduler;

    private final static long TRACK_START_TIMEOUT_SECONDS = 30;

    public ChannelTimeoutEventAdapter(Logger logger,
                                      ThreadPoolTaskScheduler scheduler,
                                      VoiceChannelService voiceChannelService,
                                      @Value("${discord.music-bot.inactivity-timeout-seconds}") int its) {
        this.logger = logger;
        this.scheduler = scheduler;
        this.inactivityTimeoutSeconds = its;
        this.voiceChannelService = voiceChannelService;
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        BlockingQueue<Runnable> tasks = ((ScheduledThreadPoolExecutor) scheduler.getScheduledExecutor()).getQueue();
        if (tasks.isEmpty()) {
            // wait up to TRACK_START_TIMEOUT_SECONDS in case timeout task has not been registered yet.
            try {
                ((ScheduledFuture<?>) tasks
                        .poll(TRACK_START_TIMEOUT_SECONDS, TimeUnit.SECONDS))
                        .cancel(false);
            } catch (InterruptedException ie) {
                throw new MusicBotException("no bot timeout was detected.", ie);
            }
        } else {
            tasks.stream()
                    .map(runnable -> (ScheduledFuture<?>) runnable)
                    .forEach(future -> future.cancel(false));
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        Runnable task = () -> {
            logger.info("no audio activity detected in last {} seconds, leaving voice channel.",
                    inactivityTimeoutSeconds);
            voiceChannelService.leaveVoiceChannel();
        };
        scheduler.schedule(task, Instant.now().plusSeconds(inactivityTimeoutSeconds));
    }
}
