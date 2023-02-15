package com.discord.music;

import com.discord.music.client.DiscordClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DiscordMusicBotApplication {

    public DiscordMusicBotApplication(DiscordClient discordClient) {
        System.out.println(discordClient.getGuildCommands());
    }

    public static void main(String[] args) {
        SpringApplication.run(DiscordMusicBotApplication.class, args);
    }

}
