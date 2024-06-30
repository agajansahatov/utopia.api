package io.github.agajansahatov.utopia.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class AuthenticationRequest {
    private String contact;
    private String password;
}
