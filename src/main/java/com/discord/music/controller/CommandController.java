package com.discord.music.controller;

import com.discord.music.model.DiscordInteractionResponse;
import com.discord.music.model.DiscordRequest;
import com.discord.music.model.InteractionResponseType;
import com.discord.music.model.InteractionType;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CommandController {
    private final Logger logger;

    public CommandController(Logger logger) {
        this.logger = logger;
    }

    @GetMapping("/health")
    public ResponseEntity<Void> health() {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/interactions")
    public ResponseEntity<DiscordInteractionResponse> interactions(@RequestBody DiscordRequest request) {
        if (request.getType() == InteractionType.PING) {
            return ResponseEntity.ok(new DiscordInteractionResponse(InteractionResponseType.PONG));
        }
        return ResponseEntity.badRequest().body(new DiscordInteractionResponse(InteractionResponseType.UPDATE_MESSAGE));
    }
}
