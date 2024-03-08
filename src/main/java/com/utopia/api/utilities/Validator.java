package com.utopia.api.utilities;

import java.util.regex.Pattern;

public class Validator {
    public static boolean isValidPhoneNumber(String str) {
        Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+?[0-9]{1,}$");
        return str != null && PHONE_NUMBER_PATTERN.matcher(str).matches();
    }

    public static boolean isValidEmail(String email) {
        if(email == null) {
            return false;
        }
        Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        return EMAIL_PATTERN.matcher(email).matches();
    }
    public static boolean isValidPassword(String password) {
        if(password == null) {
            return false;
        }
        return password.length() >= 5;
    }

    public static boolean isValidContact(String contact) {
        if(contact == null) {
            return false;
        }

        if (isValidPhoneNumber(contact)){
            return contact.length() >= 7;
        }
        return isValidEmail(contact);
    }
}
