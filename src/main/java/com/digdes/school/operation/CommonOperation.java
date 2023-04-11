package com.digdes.school.operation;

import com.digdes.school.utils.OperationUtils;

import java.util.Arrays;
import java.util.function.Predicate;

public enum CommonOperation implements Operation {
    LTE("<="),
    GTE(">="),
    NE("!="),
    LT("<"),
    GT(">"),
    EQ("="),
    LIKE("like"),
    ILIKE("ilike");

    private final String operation;

    CommonOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String getOperation() {
        return operation;
    }

    public static CommonOperation fromQuery(String query) {
        return getByFilter(operation -> OperationUtils.queryStartWithOperationIgnoreCase(query, operation.getOperation()));
    }

    public static CommonOperation of(String value) {
        return getByFilter(operation -> operation.getOperation().equals(value));
    }

    private static CommonOperation getByFilter(Predicate<CommonOperation> filter) {
        return Arrays.stream(CommonOperation.values())
                .filter(filter)
                .findAny()
                .orElse(null);
    }
}
