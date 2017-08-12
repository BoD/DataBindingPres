package org.jraf.test.util;

public class ValidationUtil {
    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }
}
