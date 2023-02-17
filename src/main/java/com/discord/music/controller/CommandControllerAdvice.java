package com.discord.music.controller;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CommandControllerAdvice extends ResponseEntityExceptionHandler {
    private final Logger logger;

    public CommandControllerAdvice(Logger logger) {
        this.logger = logger;
    }
}
