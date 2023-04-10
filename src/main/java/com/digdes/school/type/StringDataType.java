package com.digdes.school.type;

import com.digdes.school.operation.CommonOperation;
import com.digdes.school.utils.StringUtils;

import java.util.Set;
import java.util.function.Predicate;

public class StringDataType implements DataType<String> {
    private static final Set<CommonOperation> SUPPORTED_OPERATIONS = Set.of(
            CommonOperation.EQ, CommonOperation.NE,
            CommonOperation.LIKE, CommonOperation.ILIKE
    );

    @Override
    public boolean isCorrectValue(String value) {
        if (NULL_VALUE.equals(value)) {
            return true;
        }
        return value.startsWith("'") && value.endsWith("'");
    }

    @Override
    public String convertToDataType(String value) {
        if (NULL_VALUE.equals(value)) {
            return null;
        }
        return StringUtils.prepareString(value);
    }

    @Override
    public boolean isSupportedOperation(CommonOperation operation) {
        return !SUPPORTED_OPERATIONS.contains(operation);
    }

    @Override
    public Predicate<Object> getPredicateByCommonOperation(CommonOperation operation, String value) {
        Predicate<Object> defaultPredicate = object -> false;

        boolean correctValue = isCorrectValue(value);
        if (!correctValue) {
            return defaultPredicate;
        }

        return switch (operation) {
            case NE -> object -> {
                String convertedObject = convertToDataType(object.toString());
                String convertedValue = convertToDataType(value);
                if (convertedValue == null) {
                    return false;
                }
                return !convertedObject.equals(convertedValue);
            };
            case EQ -> object -> {
                String convertedObject = convertToDataType(object.toString());
                String convertedValue = convertToDataType(value);
                if (convertedValue == null) {
                    return false;
                }
                return convertedObject.equals(convertedValue);
            };
            case LIKE -> object -> {
                String convertedObject = convertToDataType(object.toString());
                String convertedValue = convertToDataType(value);
                if (convertedValue == null) {
                    return false;
                }
                convertedValue = convertedValue.replaceAll("%", ".*");
                return convertedObject.matches(convertedValue);
            };
            case ILIKE -> object -> {
                String convertedObject = convertToDataType(object.toString());
                String convertedValue = convertToDataType(value);
                if (convertedValue == null) {
                    return false;
                }
                convertedValue = "(?i)" + convertedValue.replaceAll("%", ".*");
                return convertedObject.matches(convertedValue);
            };
            default -> defaultPredicate;
        };
    }
}
