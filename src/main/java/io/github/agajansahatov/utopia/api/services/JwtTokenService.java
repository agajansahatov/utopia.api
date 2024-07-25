package io.github.agajansahatov.utopia.api.services;

import io.github.agajansahatov.utopia.api.entities.User;
import io.github.agajansahatov.utopia.api.models.requestDTOs.AuthRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenService {
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    public String generateToken(AuthRequest authRequest, boolean test) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getContact(), authRequest.getPassword())
        );

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

    public String generateToken(AuthRequest authRequest) {
        return this.generateToken(authRequest, false);
    }
}
