package com.discord.music.client;

import com.discord.music.config.properties.PublicBotProperties;
import com.discord.music.config.properties.SecretBotProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.http.HttpMethod;

import java.io.IOException;

public class DiscordClient {
    private final OkHttpClient httpClient;
    private final String baseUrl;
    private final PublicBotProperties publicBotProperties;
    private final SecretBotProperties secretBotProperties;

    private final ObjectMapper objectMapper;
    private final Headers baseRequestHeaders;

    private static final MediaType CT_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType CT_TEXT_PLAIN = MediaType.parse("text/plain");

    public DiscordClient(OkHttpClient httpClient,
                         String baseUrl,
                         PublicBotProperties publicBotProperties,
                         SecretBotProperties secretBotProperties,
                         ObjectMapper objectMapper) {
        this.baseUrl = baseUrl;
        this.httpClient = httpClient;
        this.publicBotProperties = publicBotProperties;
        this.secretBotProperties = secretBotProperties;
        this.objectMapper = objectMapper;

        this.baseRequestHeaders = buildHeaders();
    }

    public String getCommands() {
        try (Response body = httpClient.newCall(buildRequest(HttpMethod.GET, null)).execute()) {
            return body.body().string();
        } catch (IOException ioe) {
            throw new RuntimeException("request cannot be executed", ioe);
        }
    }

    private Headers buildHeaders() {
        return Headers.of(
                "Authorization", "Bot " + secretBotProperties.getBotToken(),
                "Content-Type", "application/json; charset=UTF-8"
        );
    }

    private Request buildRequest(HttpMethod method, Object requestBody) {
        RequestBody trueBody;
        if (requestBody == null) {
            trueBody = null;
        } else if (requestBody instanceof String) {
            trueBody = RequestBody.create((String) requestBody, CT_TEXT_PLAIN);
        } else {
            try {
                trueBody = RequestBody.create(objectMapper.writeValueAsString(requestBody), CT_JSON);
            } catch (JsonProcessingException jpe) {
                throw new RuntimeException(jpe);
            }
        }
        return new Request.Builder()
                .headers(this.baseRequestHeaders)
                .method(method.name(), trueBody)
                .url(this.baseUrl)
                .build();
    }

}
