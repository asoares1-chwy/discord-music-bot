package com.discord.music.model.queue;

import com.discord.music.model.YouTubeURI;

public interface ISongQueue {
    void addSong(YouTubeURI uri);
    YouTubeURI currentlyPlaying();
    void skipSong();
    void clearQueue();
}
