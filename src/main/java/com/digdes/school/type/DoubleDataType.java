package com.digdes.school.type;

import com.digdes.school.operation.CommonOperation;
import com.digdes.school.utils.NumberUtils;

import java.util.Set;
import java.util.function.Predicate;

public class DoubleDataType implements DataType<Double> {
    private static final Set<CommonOperation> SUPPORTED_OPERATIONS = Set.of(
            CommonOperation.EQ, CommonOperation.NE, CommonOperation.GTE,
            CommonOperation.GT, CommonOperation.LTE, CommonOperation.LT
    );

    @Override
    public boolean isCorrectValue(String value) {
        if (NULL_VALUE.equals(value)) {
            return true;
        }
        return NumberUtils.parseToDouble(value) != null;
    }

    @Override
    public Double convertToDataType(String value) {
        if (NULL_VALUE.equals(value)) {
            return null;
        }
        return NumberUtils.parseToDouble(value);
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
                Double converted = convertToDataType(value);
                Double convertedObject = convertToDataType(object.toString());
                if (converted == null) {
                    return false;
                }
                return convertedObject <= converted;
            };
            case GTE -> object -> {
                Double converted = convertToDataType(value);
                Double convertedObject = convertToDataType(object.toString());
                if (converted == null) {
                    return false;
                }
                return convertedObject >= converted;
            };
            case NE -> object -> {
                Double converted = convertToDataType(value);
                Double convertedObject = convertToDataType(object.toString());
                if (converted == null) {
                    return false;
                }
                return !convertedObject.equals(converted);
            };
            case LT -> object -> {
                Double converted = convertToDataType(value);
                Double convertedObject = convertToDataType(object.toString());
                if (converted == null) {
                    return false;
                }
                return convertedObject < converted;
            };
            case GT -> object -> {
                Double converted = convertToDataType(value);
                Double convertedObject = convertToDataType(object.toString());
                if (converted == null) {
                    return false;
                }
                return convertedObject > converted;
            };
            case EQ -> object -> {
                Double converted = convertToDataType(value);
                Double convertedObject = convertToDataType(object.toString());
                if (converted == null) {
                    return false;
                }
                return convertedObject.equals(converted);
            };
            default -> defaultPredicate;
        };
    }
}
