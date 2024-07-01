package io.github.agajansahatov.utopia.api.config;

import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;

import java.util.List;

public class CustomBearerTokenResolver implements BearerTokenResolver {

    private static final String HEADER_NAME = "x-auth-token";
    private final List<String> nonProtectedEndpoints;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public CustomBearerTokenResolver(List<String> nonProtectedEndpoints) {
        this.nonProtectedEndpoints = nonProtectedEndpoints;
    }

    @Override
    public String resolve(HttpServletRequest request) {
        String path = request.getRequestURI();
        for (String pattern : nonProtectedEndpoints) {
            if (pathMatcher.match(pattern, path)) {
                return null;
            }
        }

        String token = request.getHeader(HEADER_NAME);
        return token != null && !token.isEmpty() ? token : null;
    }
}
