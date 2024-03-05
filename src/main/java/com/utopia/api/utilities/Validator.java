package com.utopia.api.utilities;

import java.util.regex.Pattern;

public class Validator {
    public static boolean isValidPhoneNumber(String str) {
        Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+?[0-9]{1,}$");
        return str != null && PHONE_NUMBER_PATTERN.matcher(str).matches();
    }

    public static boolean isValidEmail(String str) {
        Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        return str != null && EMAIL_PATTERN.matcher(str).matches();
    }

    public static boolean isValidContact(String contact) {
        if(contact == null) {
            return false;
        } else if (isValidPhoneNumber(contact)){
            return contact.length() >= 7;
        } else return isValidEmail(contact);
    }
}
