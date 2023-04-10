package com.digdes.school.utils;

public final class StringUtils {
    private StringUtils() {
    }

    public static String prepareString(String value) {
        if (value == null) {
            return null;
        }
        if (value.startsWith("'") && value.endsWith("'")) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }
}
