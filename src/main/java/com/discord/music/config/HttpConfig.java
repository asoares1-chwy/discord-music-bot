package com.discord.music.config;

import com.discord.music.client.DiscordClient;
import com.discord.music.config.properties.PublicBotProperties;
import com.discord.music.config.properties.SecretBotProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpConfig {
    @Bean
    public OkHttpClient masterHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    public DiscordClient discordClient(
            OkHttpClient httpClient,
            @Value("${discord.base-url}") String baseUrl,
            PublicBotProperties pbp,
            SecretBotProperties sbp,
            ObjectMapper objectMapper) {
        return new DiscordClient(httpClient, baseUrl, pbp, sbp, objectMapper);
    }
}
