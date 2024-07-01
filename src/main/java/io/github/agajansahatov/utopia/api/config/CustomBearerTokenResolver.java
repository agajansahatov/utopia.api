package io.github.agajansahatov.utopia.api.config;

import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

import jakarta.servlet.http.HttpServletRequest;

public class CustomBearerTokenResolver implements BearerTokenResolver {
    @Override
    public String resolve(HttpServletRequest request) {
        String token = request.getHeader("x-auth-token");
        return token != null && !token.isEmpty() ? token : null;
    }
}
