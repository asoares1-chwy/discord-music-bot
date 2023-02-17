package com.discord.music.client;

import com.discord.music.config.properties.PublicBotProperties;
import com.discord.music.config.properties.SecretBotProperties;
import com.discord.music.model.ApplicationCommand;
import com.discord.music.model.ApplicationCommandRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.http.HttpMethod;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class DiscordClient {
    private final OkHttpClient httpClient;
    private final String baseUrl;
    private final String basePath;
    private final PublicBotProperties publicBotProperties;
    private final SecretBotProperties secretBotProperties;

    private final ObjectMapper objectMapper;
    private final Headers baseRequestHeaders;

    private static final MediaType CT_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType CT_TEXT_PLAIN = MediaType.parse("text/plain");

    public DiscordClient(OkHttpClient httpClient,
                         String baseUrl,
                         String basePath,
                         PublicBotProperties publicBotProperties,
                         SecretBotProperties secretBotProperties,
                         ObjectMapper objectMapper) {
        this.baseUrl = baseUrl;
        this.basePath = basePath;
        this.httpClient = httpClient;
        this.publicBotProperties = publicBotProperties;
        this.secretBotProperties = secretBotProperties;
        this.objectMapper = objectMapper;

        this.baseRequestHeaders = buildHeaders();
    }

    public List<ApplicationCommand> getGuildCommands() {
        String path = String.format("applications/%s/guilds/%s/commands",
                publicBotProperties.getAppId(), publicBotProperties.getGuildId());
        HttpUrl url = baseRequestURI().addPathSegments(path).build();
        Request request = buildRequest(url, HttpMethod.GET, null);

        return executeRequest(request, new TypeReference<>() {
        });
    }

    public void createGuildCommand(ApplicationCommandRequest command) {
        String path = String.format("applications/%s/guilds/%s/commands",
                publicBotProperties.getAppId(), publicBotProperties.getGuildId());
        HttpUrl url = baseRequestURI().addPathSegments(path).build();
        Request request = buildRequest(url, HttpMethod.POST, command);

        executeRequest(request, new TypeReference<Void>() {
        });
    }

    private <T> T executeRequest(Request request, TypeReference<T> jType) {
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new DiscordClientException(request.method(), request.url(), response.code(), response.message());
            }
            if (jType.getType().getTypeName().equals("void")) {
                return null;
            }
            InputStream bodyStream = Objects.requireNonNull(response.body()).byteStream();
            return objectMapper.readValue(bodyStream, jType);
        } catch (Exception ex) {
            throw new RuntimeException("request to Discord API cannot be executed", ex);
        }
    }

    private Headers buildHeaders() {
        return Headers.of(
                "Authorization", "Bot " + secretBotProperties.getDiscordToken(),
                "Content-Type", "application/json; charset=UTF-8"
        );
    }

    private Request buildRequest(HttpUrl url, HttpMethod method, Object requestBody) {
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
                .url(url)
                .build();
    }

    private HttpUrl.Builder baseRequestURI() {
        return new HttpUrl.Builder()
                .scheme("https")
                .host(this.baseUrl)
                .addPathSegments(this.basePath);
    }

}
