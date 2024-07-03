package io.github.agajansahatov.utopia.api.services;

import io.github.agajansahatov.utopia.api.entities.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtTokenService {
    private final JwtEncoder jwtEncoder;

    public JwtTokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        // Assuming that getAuthorities() returns a collection with exactly one element (role)
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        User user = (User) authentication.getPrincipal();
        String fullName = user.getFirstname() + " " + user.getLastname();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("utopia.api")
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.DAYS))
                .claim("id", user.getId())
                .subject(fullName)
                .claim("role", role)
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
