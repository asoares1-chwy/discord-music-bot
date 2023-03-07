package com.discord.music.component;

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

@Component
public class ChannelTimeoutEventAdapter extends AudioEventAdapter {
    private final Logger logger;
    private final int inactivityTimeoutSeconds;
    private final ThreadPoolTaskScheduler scheduler;
    private final Runnable trackEndRunnable;

    public ChannelTimeoutEventAdapter(Logger logger,
                                      ThreadPoolTaskScheduler scheduler,
                                      VoiceChannelService voiceChannelService,
                                      @Value("${discord.music-bot.inactivity-timeout-seconds}") int its) {
        this.logger = logger;
        this.scheduler = scheduler;
        this.inactivityTimeoutSeconds = its;
        this.trackEndRunnable = voiceChannelService::leaveVoiceChannel;
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        BlockingQueue<Runnable> tasks = ((ScheduledThreadPoolExecutor) scheduler.getScheduledExecutor()).getQueue();
        logger.info("detected audio activity as track {} starting, cancelling timeout.", track.getIdentifier());
        tasks.stream()
                .map(runnable -> (ScheduledFuture<?>) runnable)
                .forEach(future -> future.cancel(false));
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        scheduler.schedule(trackEndRunnable, Instant.now().plusSeconds(inactivityTimeoutSeconds));
    }
}
