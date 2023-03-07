package com.discord.music.config;

import com.discord.music.config.properties.SecretBotProperties;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscordGatewayClientConfig {
    @Bean
    GatewayDiscordClient createGatewayClient(SecretBotProperties sbp) {
        return DiscordClientBuilder
                .create(sbp.getDiscordToken()).build()
                .login().block();
    }
}
