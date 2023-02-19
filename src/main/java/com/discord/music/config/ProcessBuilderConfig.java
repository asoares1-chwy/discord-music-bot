package com.discord.music.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class ProcessBuilderConfig {
    @Bean
    ProcessBuilder pythonDownloadScriptProcessBuilder() throws IOException {
        Resource resource = new ClassPathResource("download_youtube_video.py");
        return new ProcessBuilder("/opt/homebrew/bin/python3", resource.getFile().getAbsolutePath(),
                "https://www.youtube.com/watch?v=AA-S8CGoFug")
                .redirectErrorStream(false);
    }
}
