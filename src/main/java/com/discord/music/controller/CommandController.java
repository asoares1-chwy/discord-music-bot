package com.discord.music.controller;

import com.discord.music.model.InteractionRequest;
import com.discord.music.model.InteractionResponse;
import com.discord.music.model.InteractionResponseData;
import com.discord.music.model.InteractionResponseType;
import com.discord.music.service.DiscordCommandService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

// @Controller
public class CommandController {
    private final Logger logger;
    private final DiscordCommandService discordService;

    public CommandController(DiscordCommandService discordService, Logger logger) {
        this.discordService = discordService;
        this.logger = logger;
    }

    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public void health() {
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/interactions")
    public InteractionResponse interactions(@RequestBody InteractionRequest request) {
        return switch (request.type()) {
            case PING -> new InteractionResponse(InteractionResponseType.PONG);
            case APPLICATION_COMMAND -> discordService.handleInteractionCommand(request);
            default -> new InteractionResponse(InteractionResponseType.CHANNEL_MESSAGE_WITH_SOURCE,
                    new InteractionResponseData("command not understood."));
        };
    }
}
