package com.discord.music.model.queue;

import org.springframework.context.ApplicationEvent;

public class SongEvent extends ApplicationEvent {
    private final SongEventType eventType;

    public SongEvent(Object source, SongEventType eventType) {
        super(source);
        this.eventType = eventType;
    }

    public SongEventType getEventType() {
        return eventType;
    }
}
