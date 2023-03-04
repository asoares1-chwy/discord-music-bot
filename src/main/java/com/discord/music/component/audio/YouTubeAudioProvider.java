package com.discord.music.component.audio;

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import discord4j.voice.AudioProvider;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

@Component
public class YouTubeAudioProvider extends AudioProvider {
    private final AudioPlayer audioPlayer;
    private final MutableAudioFrame audioFrame;

    public YouTubeAudioProvider(AudioPlayer audioPlayer) {
        super(ByteBuffer
                .allocate(StandardAudioDataFormats.DISCORD_OPUS.maximumChunkSize()));

        this.audioPlayer = audioPlayer;
        this.audioFrame = new MutableAudioFrame();

        audioFrame.setBuffer(this.getBuffer());
    }

    @Override
    public boolean provide() {
        boolean didProvide = this.audioPlayer.provide(this.audioFrame);
        if (didProvide) {
            this.getBuffer().flip();
        }
        return didProvide;
    }

    public AudioFrame getAudioFrame() {
        return this.audioFrame;
    }
}
