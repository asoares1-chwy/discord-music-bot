package com.discord.music.config;

import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.protocol.CreateTunnel;
import com.github.alexdlaird.ngrok.protocol.Proto;
import com.github.alexdlaird.ngrok.protocol.Tunnel;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// @Configuration
@Profile({"local"})
public class NgrockConfig {
    private final Logger logger;

    public NgrockConfig(Logger logger) {
        this.logger = logger;
        logger.info("initializing ngrok server to communicate with discord api.");
    }

    @Bean
    NgrokClient ngrokClient() {
        return new NgrokClient.Builder().build();
    }

    @Bean
    Tunnel httpTunnel(NgrokClient ngrokClient, @Value("${server.port}") String port) {
        final CreateTunnel createTunnel = new CreateTunnel.Builder()
                .withName("discord-music-bot")
                .withProto(Proto.HTTP)
                .withAddr(Integer.parseInt(port))
                .build();
        Tunnel t = ngrokClient.connect(createTunnel);
        logger.info("--- PUBLIC URI OF SERVER: <{}> --- PROVIDE THIS TO DISCORD ---", t.getPublicUrl());
        return t;
    }
}
