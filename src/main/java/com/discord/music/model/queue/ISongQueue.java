package com.discord.music.model.queue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.List;

/**
 * Defines a set of operations for a song queue.
 */
public interface ISongQueue {
    /**
     * Appends a song to the end of the queue.
     * @param uri the YouTube resource to play.
     */
    void addSong(AudioTrack uri);

    /**
     * Returns the currently playing song, if one exists.
     * @return the song at the head of the queue, or null if nothing is playing.
     */
    AudioTrack currentlyPlaying();

    /**
     * Removes the song at the head of the queue. An event is published to inform the player.
     * @return true if the song was skipped, and false otherwise.
     */
    boolean skipSong();

    /**
     * Removes all elements from the queue. An event is published to inform the player.
     * @return true if the queue was cleared, or if a track was stopped; otherwise false.
     */
    boolean clearQueue();

    /**
     * Returns a list view of the remaining songs in the queue. Does not return the currently playing track.
     * @return A List containing an ordered list of songs in the queue.
     */
    List<AudioTrack> peekQueueContents();
}
