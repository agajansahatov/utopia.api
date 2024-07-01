package io.github.agajansahatov.utopia.api.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ContactValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Contact {
    String message() default "Invalid contact information";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
