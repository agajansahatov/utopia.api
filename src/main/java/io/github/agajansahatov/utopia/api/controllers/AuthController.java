package io.github.agajansahatov.utopia.api.controllers;

import io.github.agajansahatov.utopia.api.models.requests.AuthRequest;
import io.github.agajansahatov.utopia.api.services.JwtTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService tokenService;

    @PostMapping
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getContact(), authRequest.getPassword())
            );
            return ResponseEntity.ok(tokenService.generateToken(authentication));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid credentials");
        }
    }
}
