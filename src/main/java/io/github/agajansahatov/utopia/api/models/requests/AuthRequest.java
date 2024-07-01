package io.github.agajansahatov.utopia.api.models.requests;

import io.github.agajansahatov.utopia.api.validators.Contact;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthRequest {
    @NotBlank(message = "Contact is required. You should either provide an email or a phone number.")
    @Contact(message = "Contact should be a valid email or phone number")
    private String contact;

    @NotBlank(message = "Password is required")
    @Size(min = 5, max = 255, message = "Password must be between 5 and 255 characters")
    private String password;
}
