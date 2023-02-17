package com.discord.music.controller;

import com.discord.music.model.DiscordInteractionResponse;
import com.discord.music.model.DiscordRequest;
import com.discord.music.model.InteractionResponseType;
import com.discord.music.model.InteractionType;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller("/v1/api/")
public class CommandController {
    private final Logger logger;

    public CommandController(Logger logger) {
        this.logger = logger;
    }

    @GetMapping("health")
    @ResponseStatus(HttpStatus.OK)
    public void health() {
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("interactions")
    public DiscordInteractionResponse interactions(@RequestBody DiscordRequest request) {
        if (request.getType() == InteractionType.PING) {
            return new DiscordInteractionResponse(InteractionResponseType.PONG);
        }
        return new DiscordInteractionResponse(InteractionResponseType.UPDATE_MESSAGE);
    }
}
