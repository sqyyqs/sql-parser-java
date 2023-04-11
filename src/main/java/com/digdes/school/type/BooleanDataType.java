package com.digdes.school.type;

import com.digdes.school.operation.CommonOperation;

import java.util.Set;
import java.util.function.Predicate;

public class BooleanDataType implements DataType<Boolean> {
    private static final Set<CommonOperation> SUPPORTED_OPERATIONS = Set.of(
            CommonOperation.EQ, CommonOperation.NE
    );

    @Override
    public boolean isCorrectValue(String value) {
        if (NULL_VALUE.equals(value)) {
            return true;
        }
        return Boolean.TRUE.toString().equals(value) || Boolean.FALSE.toString().equals(value);
    }

    @Override
    public Boolean convertToDataType(String value) {
        if (NULL_VALUE.equals(value)) {
            return null;
        }
        return Boolean.valueOf(value);
    }

    @Override
    public boolean isSupportedOperation(CommonOperation operation) {
        return SUPPORTED_OPERATIONS.contains(operation);
    }

    @Override
    public Predicate<Object> getPredicateByCommonOperation(CommonOperation operation, String value) {
        Predicate<Object> defaultPredicate = object -> false;

        boolean correctValue = isCorrectValue(value);
        if (!correctValue) {
            return defaultPredicate;
        }

        return switch (operation) {
            case EQ -> object -> {
                Boolean converted = convertToDataType(value);

                if (converted == null) {
                    return object == null;
                }

                return converted.equals(object);
            };
            case NE -> object -> {
                Boolean converted = convertToDataType(value);

                if (converted == null) {
                    return object == null;
                }

                return !converted.equals(object);
            };
            default -> defaultPredicate;
        };
    }
}
