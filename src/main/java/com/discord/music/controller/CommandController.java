package com.discord.music.controller;

import okhttp3.Response;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommandController {
    private final Logger logger;

    public CommandController(Logger logger) {
        this.logger = logger;
    }

    @GetMapping("/interactions")
    public ResponseEntity<String> interactions(String request) {
        logger.info("checking interactions: {}", request);
        return ResponseEntity.ok("ok");
    }
}
