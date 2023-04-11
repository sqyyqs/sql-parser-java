package com.digdes.school.type;

import com.digdes.school.operation.CommonOperation;
import com.digdes.school.utils.NumberUtils;

import java.util.Set;
import java.util.function.Predicate;

public class LongDataType implements DataType<Long> {
    private static final Set<CommonOperation> SUPPORTED_OPERATIONS = Set.of(
            CommonOperation.EQ, CommonOperation.NE, CommonOperation.GTE,
            CommonOperation.GT, CommonOperation.LTE, CommonOperation.LT
    );

    @Override
    public boolean isCorrectValue(String value) {
        if (NULL_VALUE.equals(value)) {
            return true;
        }
        return NumberUtils.parseToLong(value) != null;
    }

    @Override
    public Long convertToDataType(String value) {
        if (NULL_VALUE.equals(value)) {
            return null;
        }
        return NumberUtils.parseToLong(value);
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
            case LTE -> object -> {
                Long converted = convertToDataType(value);
                Long convertedObject = convertToDataType(object.toString());
                if (converted == null) {
                    return false;
                }
                return convertedObject <= converted;
            };
            case GTE -> object -> {
                Long converted = convertToDataType(value);
                Long convertedObject = convertToDataType(object.toString());
                if (converted == null) {
                    return false;
                }
                return convertedObject >= converted;
            };
            case NE -> object -> {
                Long converted = convertToDataType(value);
                Long convertedObject = convertToDataType(object.toString());
                if (converted == null) {
                    return false;
                }
                return !convertedObject.equals(converted);
            };
            case LT -> object -> {
                Long converted = convertToDataType(value);
                Long convertedObject = convertToDataType(object.toString());
                if (converted == null) {
                    return false;
                }
                return convertedObject < converted;
            };
            case GT -> object -> {
                Long converted = convertToDataType(value);
                Long convertedObject = convertToDataType(object.toString());
                if (converted == null) {
                    return false;
                }
                return convertedObject > converted;
            };
            case EQ -> object -> {
                Long converted = convertToDataType(value);
                Long convertedObject = convertToDataType(object.toString());
                if (converted == null) {
                    return false;
                }
                return convertedObject.equals(converted);
            };
            default -> defaultPredicate;
        };
    }
}
