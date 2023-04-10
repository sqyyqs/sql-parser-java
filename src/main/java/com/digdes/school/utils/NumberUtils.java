package com.digdes.school.utils;

public final class NumberUtils {
    private NumberUtils() {
    }

    public static Long parseToLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    public static Double parseToDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    public static boolean isNumeric(String value) {
        if (value == null) {
            return false;
        }
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
}
