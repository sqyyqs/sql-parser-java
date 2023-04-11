package com.digdes.school.utils;

public final class OperationUtils {
    private OperationUtils() {
    }

    public static boolean queryStartWithOperationIgnoreCase(String query, String operation) {
        return query.trim().toLowerCase().startsWith(operation.toLowerCase());
    }
}
