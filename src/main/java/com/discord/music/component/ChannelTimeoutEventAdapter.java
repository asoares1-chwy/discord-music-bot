package com.discord.music.component;

import com.discord.music.service.VoiceChannelService;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class responsible for timing out the bot after a period of inactivity.
 * When a song ends, a job is scheduled to remove the bot after the specified timeout.
 * When a song begins, this job is removed from the queue.
 */
@Component
public class ChannelTimeoutEventAdapter extends AudioEventAdapter {
    private final Logger logger;
    private final TaskScheduler scheduler;
    private final VoiceChannelService voiceChannelService;
    private final int inactivityTimeoutSeconds;

    public ChannelTimeoutEventAdapter(Logger logger,
                                      @Qualifier("ChannelTimeout") TaskScheduler scheduler,
                                      VoiceChannelService voiceChannelService,
                                      @Value("${discord.music-bot.inactivity-timeout-seconds}") int its) {
        this.logger = logger;
        this.scheduler = scheduler;
        this.inactivityTimeoutSeconds = its;
        this.voiceChannelService = voiceChannelService;
    }

    private final AtomicReference<ScheduledFuture<?>> botTimeoutFuture = new AtomicReference<>();

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        /*
         This is not a perfect calculation. Delays in loading the track may cause this trigger to fire early.
         In practice, this is an acceptable margin of error and the bot will leave within ±1 second of expected Instant.
         */
        Instant trackEndTime = Instant.now()
                .plusSeconds(inactivityTimeoutSeconds)
                .plusMillis(track.getDuration());

        ScheduledFuture<?> currentTask = botTimeoutFuture.getAcquire();
        if (currentTask != null) {
            currentTask.cancel(false);
        }

        Runnable scheduleTask = () -> {
            logger.info("no voice activity detected for {} seconds after track {}, leaving channel.",
                    inactivityTimeoutSeconds, track.getIdentifier());
            voiceChannelService.leaveVoiceChannel();
        };
        ScheduledFuture<?> sf = scheduler.schedule(scheduleTask, trackEndTime);

        botTimeoutFuture.set(sf);
        logger.info("track {} started, bot will leave channel at {}", track.getIdentifier(), trackEndTime);
    }

}
