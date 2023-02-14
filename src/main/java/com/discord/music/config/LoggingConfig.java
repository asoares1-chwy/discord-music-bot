package com.discord.music.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Objects;

@Configuration
public class LoggingConfig {
    @Bean
    @Scope("prototype")
    Logger createLogger(InjectionPoint injectionPoint) {
        return LoggerFactory
                .getLogger(Objects.requireNonNull(injectionPoint.getMethodParameter()).getContainingClass());
    }
}
