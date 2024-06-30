package io.github.agajansahatov.utopia.api.filters;

import io.github.agajansahatov.utopia.api.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final UserService userService;

    public JwtRequestFilter(JwtDecoder jwtDecoder, UserService userService) {
        this.jwtDecoder = jwtDecoder;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("x-auth-token");

        if (authHeader != null) {
            try {
                Jwt jwt = jwtDecoder.decode(authHeader);
                UserDetails userDetails = userService.loadUserByUsername(jwt.getSubject());

                if (userDetails != null) {
                    JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt, userDetails.getAuthorities(), userDetails.getUsername());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JwtException | UsernameNotFoundException e) {
                // Invalid token or user not found
            }
        }

        chain.doFilter(request, response);
    }
}
