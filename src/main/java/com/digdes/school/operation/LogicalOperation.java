package com.digdes.school.operation;

import com.digdes.school.utils.OperationUtils;

import java.util.Arrays;
import java.util.function.Predicate;

public enum LogicalOperation implements Operation {
    WHERE("where"),
    AND("and"),
    OR("or");

    private final String operation;

    LogicalOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String getOperation() {
        return operation;
    }

    public static LogicalOperation fromQuery(String query) {
        return Arrays.stream(values())
                .filter(operation -> OperationUtils.queryStartWithOperationIgnoreCase(query, operation.getOperation()))
                .findFirst()
                .orElse(null);
    }

    public static LogicalOperation of(String value) {
        return getByFilter(operation1 -> operation1.getOperation().equalsIgnoreCase(value));
    }

    private static LogicalOperation getByFilter(Predicate<LogicalOperation> filter) {
        return Arrays.stream(LogicalOperation.values())
                .filter(filter)
                .findAny()
                .orElse(null);
    }
}
