package com.lisap.equus.utils;

import com.lambdapioneer.argon2kt.Argon2Kt;
import com.lambdapioneer.argon2kt.Argon2KtResult;
import com.lambdapioneer.argon2kt.Argon2Mode;

public class Utils {

    public static String capitalize(String str) {
        if (str == null) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static boolean isPasswordValid(String stableEncodedPassword, String password) {
        final Argon2Kt argon2Kt = new Argon2Kt();
        return argon2Kt.verify(Argon2Mode.ARGON2_I, stableEncodedPassword, password.getBytes());
    }
}
