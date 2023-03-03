package com.discord.music.model.queue;

import com.discord.music.model.YouTubeURI;

/**
 * Defines a set of operations for a song queue.
 */
public interface ISongQueue {
    /**
     * Appends a song to the end of the queue.
     * @param uri the YouTube resource to play.
     */
    void addSong(YouTubeURI uri);

    /**
     * Returns the song at the head of the queue. Does not modify the queue.
     * @return the song at the head of the queue.
     */
    YouTubeURI currentlyPlaying();

    /**
     * Removes the song at the head of the queue. An event is published to inform the player.
     */
    boolean skipSong();

    /**
     * Removes all elements from the queue. An event is published to inform the player.
     */
    boolean clearQueue();
}
