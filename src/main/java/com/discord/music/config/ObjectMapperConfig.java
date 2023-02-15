package com.discord.music.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {
    @Bean("masterObjectMapper")
    public ObjectMapper masterObjectMapper() {
        return JsonMapper.builder()
                .configure(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS, false)
                .build()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }
}
