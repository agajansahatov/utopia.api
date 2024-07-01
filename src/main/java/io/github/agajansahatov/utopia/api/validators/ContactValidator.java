package io.github.agajansahatov.utopia.api.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ContactValidator implements ConstraintValidator<Contact, String> {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String PHONE_REGEX = "^\\+?[0-9]{1,}$";

    @Override
    public void initialize(Contact constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }

        boolean isEmail = Pattern.compile(EMAIL_REGEX).matcher(value).matches();
        boolean isPhone = Pattern.compile(PHONE_REGEX).matcher(value).matches();

        if (isPhone && value.length() <= 7)
            isPhone = false;

        return isEmail || isPhone;
    }
}

