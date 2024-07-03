package io.github.agajansahatov.utopia.api.services;

import io.github.agajansahatov.utopia.api.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class JwtTokenService {
    private final JwtEncoder jwtEncoder;

    public JwtTokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication, boolean test) {
        log.debug(String.valueOf(test));

        Instant now = Instant.now();

        // default value is false, which is set in the overloaded method
        if(test) {
            now = now.minusSeconds(5L);
        }

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

    public String generateToken(Authentication authentication) {
        return this.generateToken(authentication, false);
    }
}
