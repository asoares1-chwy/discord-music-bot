package com.discord.music.component;

import com.discord.music.model.YouTubeURI;
import com.discord.music.model.queue.ISongQueue;
import com.discord.music.model.queue.SongEvent;
import com.discord.music.model.queue.SongEventType;
import jakarta.annotation.Nullable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;

@Component
public class SongQueue implements ApplicationListener<SongEvent>, ISongQueue {
    private final Deque<YouTubeURI> queue;
    private final ApplicationEventPublisher publisher;

    public SongQueue(ApplicationEventPublisher publisher) {
        this.queue = new ArrayDeque<>();
        this.publisher = publisher;
    }

    @Override
    public void onApplicationEvent(SongEvent event) {
        SongEventType eventType = event.getEventType();
        if (eventType != SongEventType.SONG_ENDED || this.queue.isEmpty()) {
            return;
        }
        this.queue.removeFirst();
        SongEvent songStartedEvent = new SongEvent(this, SongEventType.SONG_STARTED);
        this.publisher.publishEvent(songStartedEvent);
    }

    @Override
    public void addSong(YouTubeURI uri) {
        this.queue.add(uri);
    }

    @Override
    @Nullable
    public YouTubeURI currentlyPlaying() {
        return this.queue.peekFirst();
    }

    @Override
    public void skipSong() {
        if (this.queue.isEmpty()) {
            return;
        }
        this.queue.pollFirst();
        SongEvent songSkippedEvent = new SongEvent(this, SongEventType.SONG_SKIPPED);
        this.publisher.publishEvent(songSkippedEvent);
    }

    @Override
    public void clearQueue() {
        if (this.queue.isEmpty()) {
            return;
        }
        this.queue.clear();
        SongEvent queueClearedEvent = new SongEvent(this, SongEventType.QUEUE_CLEARED);
        this.publisher.publishEvent(queueClearedEvent);
    }

}
