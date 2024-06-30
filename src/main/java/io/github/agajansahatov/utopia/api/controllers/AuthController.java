package io.github.agajansahatov.utopia.api.controllers;

import io.github.agajansahatov.utopia.api.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService tokenService;

    @PostMapping
    public String authenticate(@RequestBody Map<String, String> userCredentials) {
        try {
            String username = userCredentials.get("contact");
            String password = userCredentials.get("password");
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return tokenService.generateToken(authentication);
        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }
}
