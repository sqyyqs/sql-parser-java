package com.digdes.school.type;

import com.digdes.school.operation.CommonOperation;

import java.util.function.Predicate;

public interface DataType<T> {
     String NULL_VALUE = "null";

     boolean isCorrectValue(String value);

     T convertToDataType(String value);

     boolean isSupportedOperation(CommonOperation operation);

     Predicate<Object> getPredicateByCommonOperation(CommonOperation operation, String value);
}
