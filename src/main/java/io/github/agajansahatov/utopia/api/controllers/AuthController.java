package io.github.agajansahatov.utopia.api.controllers;

import io.github.agajansahatov.utopia.api.models.requestDTOs.AuthRequest;
import io.github.agajansahatov.utopia.api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(AuthController.ENDPOINT_PATH)
@RequiredArgsConstructor
public class AuthController {
    public static final String ENDPOINT_PATH = "/api/auth";
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthRequest authRequest) {
        try {
            // The authenticationManager in the userService.generateToken() method validates the user credentials
            return ResponseEntity.ok(userService.generateToken(authRequest.getContact(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials");
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed for some reason in the server", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }
}
