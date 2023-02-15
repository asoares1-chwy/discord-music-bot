package com.discord.music.controller.filters;

import com.discord.music.config.properties.PublicBotProperties;
import jakarta.servlet.Filter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class InteractionFilterChain {
    private static final String conditionalProperty = "discord.authentication.interaction-filter-enabled";

    @Bean
    @ConditionalOnProperty(value = conditionalProperty, havingValue = "true")
    public SecurityFilterChain filterChainInteractions(HttpSecurity http, PublicBotProperties pbp) throws Exception {
        Filter filter = new InteractionFilter(pbp.getPublicKey());
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/interactions").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/health").permitAll()
                        .anyRequest().denyAll()
                )
                .csrf().disable()
                .addFilterBefore(filter, BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @ConditionalOnProperty(value = conditionalProperty, havingValue = "false")
    public SecurityFilterChain filterChainNoInteractions(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll()).csrf().disable();
        return http.build();
    }
}
