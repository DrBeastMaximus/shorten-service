package com.topsquad.shortenservice.util;

import org.springframework.stereotype.Component;

import java.util.Random;

public class CodeGeneratorUtil {
    private static final char[] ALL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    public static String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALL_CHARS.length);
            code.append(ALL_CHARS[index]);
        }
        return code.toString();
    }
}
