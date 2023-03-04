package com.discord.music.config;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AudioPlayerConfig {
    @Bean
    AudioPlayerManager getAudioPlayerManager() {
        return new DefaultAudioPlayerManager();
    }
}
