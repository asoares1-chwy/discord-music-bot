package com.discord.music.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.web.filter.GenericFilterBean;
import software.pando.crypto.nacl.Crypto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

/**
 * Spring Security filter to verify the signature of the request.
 */
public class InteractionFilter extends GenericFilterBean {
    private final String key;

    private final static HexFormat hexFormatter = HexFormat.of();

    public InteractionFilter(String key) {
        this.key = key;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        CachedBodyHttpServletRequest cachedReq = new CachedBodyHttpServletRequest((HttpServletRequest) request);

        String signature = cachedReq.getHeader("X-Signature-Ed25519");
        String timestamp = cachedReq.getHeader("X-Signature-Timestamp");

        if (signature == null || timestamp == null || key == null) {
            unauthorized(response);
            return;
        }

        String body = IOUtils.toString(cachedReq.getInputStream(), StandardCharsets.UTF_8);

        byte[] keyHex, signatureHex;
        try {
            keyHex = hexFormatter.parseHex(key);
            signatureHex = hexFormatter.parseHex(signature);
        } catch (IllegalArgumentException iae) {
            unauthorized(response);
            return;
        }

        boolean isVerified = Crypto.signVerify(
                Crypto.signingPublicKey(keyHex), (timestamp + body).getBytes(StandardCharsets.UTF_8), signatureHex);

        if (isVerified) {
            chain.doFilter(cachedReq, response);
        } else {
            unauthorized(response);
        }
    }

    private static void unauthorized(ServletResponse response) throws IOException {
        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
