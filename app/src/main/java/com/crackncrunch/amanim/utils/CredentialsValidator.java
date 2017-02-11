package com.crackncrunch.amanim.utils;

public class CredentialsValidator {

    public static boolean isValidEmail(String email) {
        return !email.trim().isEmpty() &&
                AppUtils.EMAIL_ADDRESS_VALIDATE.matcher(email.trim()).matches();
    }

    public static boolean isValidPassword(String password) {
        return !password.trim().isEmpty() &&
                AppUtils.PASSWORD_VALIDATE.matcher(password.trim()).matches();
    }
}
