package com.jewelry.util;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtil {
    private static final int STRENGTH = 12;

    public static String hash(String password) {
        return BCrypt.withDefaults().hashToString(STRENGTH, password.toCharArray());
    }

    public static boolean verify(String password, String hash) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hash);
        return result.verified;
    }
}
