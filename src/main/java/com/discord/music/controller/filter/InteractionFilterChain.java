package com.discord.music.controller.filter;

import com.discord.music.config.properties.PublicBotProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class InteractionFilterChain {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, PublicBotProperties pbp) throws Exception {
        http.addFilterAfter(new InteractionFilter(pbp.getPublicKey()), BasicAuthenticationFilter.class);
        return http.build();
    }
}
