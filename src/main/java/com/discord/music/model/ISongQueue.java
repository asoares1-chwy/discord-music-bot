package com.discord.music.model;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.List;

/**
 * Defines a set of operations for a Song Queue.
 */
public interface ISongQueue {
    /**
     * Appends a song to the end of the queue. If the queue is empty, the AudioTrack will play immediately.
     *
     * @param uri the AudioTrack resource to play.
     */
    void addSong(AudioTrack uri);

    /**
     * Returns the currently playing song, if one exists.
     *
     * @return the song at the head of the queue, or null if nothing is playing.
     */
    AudioTrack currentlyPlaying();

    /**
     * Cancels the currently playing track. If the queue contains more elements, the next track will begin playing.
     *
     * @return true if the song was skipped, and false otherwise.
     */
    boolean skipSong();

    /**
     * Removes all elements from the queue. Cancels the currently playing AudioTrack.
     * The queue will always be empty after this operation.
     *
     * @return true if the queue was cleared, or if a track was stopped; otherwise false.
     */
    boolean clearQueue();

    /**
     * Returns an immutable list view of the remaining songs in the queue.
     * Does not return the currently playing track.
     *
     * @return An ordered list of songs in the queue.
     */
    List<AudioTrack> peekQueueContents();

    /**
     * Determines if the bot is currently active. Active status is defined as:<p></p>
     * 1. A song is currently playing. <p></p>
     * 2. There are songs remaining in the queue.
     *
     * @return true if the bot is currently active, and false otherwise.
     */
    boolean isActiveState();
}
