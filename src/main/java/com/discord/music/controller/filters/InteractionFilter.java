package com.discord.music.controller.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import software.pando.crypto.nacl.Crypto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

/**
 * Spring Security filter to verify the signature of the request.
 */
public class InteractionFilter extends OncePerRequestFilter {
    private final String key;

    private final static HexFormat hexFormatter = HexFormat.of();
    private final static RequestMatcher negationMatcher =
            new NegatedRequestMatcher(new AntPathRequestMatcher("/interactions", HttpMethod.POST.name()));
    private final static Logger logger = LoggerFactory.getLogger(InteractionFilter.class);

    public InteractionFilter(String key) {
        this.key = key;
    }

    /**
     * The request validation should only occur
     * @param request current HTTP request
     * @return true if the endpoint does NOT match a POST request for interactions, and false otherwise.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return negationMatcher.matches(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        CachedBodyHttpServletRequest cachedReq = new CachedBodyHttpServletRequest(request);
        try {
            if (checkCryptoVerified(cachedReq)) {
                chain.doFilter(cachedReq, response);
            } else {
                unauthorized(response);
            }
        } catch (Exception ex) {
            logger.error("could not authorize due to exception. The status code will remain 401 - Unauthorized.", ex);
            unauthorized(response);
        }
    }

    private boolean checkCryptoVerified(HttpServletRequest request) throws IOException {
        String signature = request.getHeader("X-Signature-Ed25519");
        String timestamp = request.getHeader("X-Signature-Timestamp");

        if (signature == null || timestamp == null || key == null) {
            return false;
        }

        String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);

        byte[] keyHex = hexFormatter.parseHex(key);
        byte[] signatureHex = hexFormatter.parseHex(signature);

        return Crypto.signVerify(
                Crypto.signingPublicKey(keyHex), (timestamp + body).getBytes(StandardCharsets.UTF_8), signatureHex);
    }

    private static void unauthorized(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
