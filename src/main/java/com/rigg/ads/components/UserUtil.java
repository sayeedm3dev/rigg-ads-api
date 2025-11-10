package com.rigg.ads.components;

import java.security.SecureRandom;

public class UserUtil {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public static String generateUsername(String firstName) {
        String[] words = {"star", "moon", "sky", "cloud", "sun", "wind", "tree", "leaf"};
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(firstName.toLowerCase());
        for (int i = 0; i < 2; i++) { // 2 random words
            sb.append(words[random.nextInt(words.length)]);
        }
        sb.append(random.nextInt(100)); // number for uniqueness
        return sb.toString();
    }
}

