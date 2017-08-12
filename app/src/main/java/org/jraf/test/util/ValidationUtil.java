package org.jraf.test.util;

import android.util.Patterns;

public class ValidationUtil {
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
