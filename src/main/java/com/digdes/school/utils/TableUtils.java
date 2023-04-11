package com.digdes.school.utils;

import com.digdes.school.domain.LexemeEntity;
import com.digdes.school.exception.ValidationException;
import com.digdes.school.operation.CommonOperation;
import com.digdes.school.operation.LexemeType;
import com.digdes.school.type.BooleanDataType;
import com.digdes.school.type.DataType;
import com.digdes.school.type.DoubleDataType;
import com.digdes.school.type.LongDataType;
import com.digdes.school.type.StringDataType;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public final class TableUtils {
    private static final Map<String, DataType<?>> COLUMN_TO_TYPE = new HashMap<>();

    private static final Set<LexemeType> DATA_TYPES = Set.of(LexemeType.BOOLEAN_TYPE,
            LexemeType.DOUBLE_TYPE, LexemeType.NULL_TYPE, LexemeType.STRING_TYPE, LexemeType.LONG_TYPE);

    static {
        LongDataType longDataType = new LongDataType();

        COLUMN_TO_TYPE.put("id", longDataType);
        COLUMN_TO_TYPE.put("lastName", new StringDataType());
        COLUMN_TO_TYPE.put("cost", new DoubleDataType());
        COLUMN_TO_TYPE.put("active", new BooleanDataType());
        COLUMN_TO_TYPE.put("age", longDataType);
    }

    private TableUtils() {
    }

    public static Map.Entry<String, Object> prepareEntry(LexemeEntity leftOperand, LexemeEntity operator,
                                                         LexemeEntity rightOperand) throws ValidationException {
        if (leftOperand.type() != LexemeType.STRING_TYPE) {
            throw new ValidationException("Invalid column name.");
        }
        CommonOperation operation = CommonOperation.of(operator.value());
        if (operator.type() != LexemeType.COMMON_OPERATION_TYPE || operation != CommonOperation.EQ) {
            throw new ValidationException("Invalid operation.");
        }
        if (!DATA_TYPES.contains(rightOperand.type())) {
            throw new ValidationException("Incompatible types.");
        }

        String resultColumnName = null;
        Object resultObject = null;

        for (String columnName : COLUMN_TO_TYPE.keySet()) {
            if (columnName.equalsIgnoreCase(StringUtils.prepareString(leftOperand.value()))) {
                resultColumnName = columnName;
                DataType<?> dataType = COLUMN_TO_TYPE.get(columnName);
                if (dataType.isCorrectValue(rightOperand.value())) {
                    resultObject = dataType.convertToDataType(rightOperand.value());
                } else {
                    throw new ValidationException("Incompatible types.");
                }
            }
        }

        if (resultColumnName == null) {
            throw new ValidationException("Validation error.");
        }
        return new AbstractMap.SimpleEntry<>(resultColumnName, resultObject);
    }


    public static Predicate<Map<String, Object>> prepareOperationEntry(LexemeEntity leftOperand, LexemeEntity operation,
                                                                       LexemeEntity rightOperand) throws ValidationException {
        String column = COLUMN_TO_TYPE.keySet()
                .stream()
                .filter(columnName -> columnName.equalsIgnoreCase(StringUtils.prepareString(leftOperand.value())))
                .findFirst()
                .orElse(null);
        if (column == null) {
            throw new ValidationException("Invalid entry.");
        }

        DataType<?> rightOperandDataType = COLUMN_TO_TYPE.get(column);

        CommonOperation commonOperation = CommonOperation.of(operation.value());
        if (rightOperandDataType.isSupportedOperation(commonOperation)) {
            throw new ValidationException("Incompatible types.");
        }

        Predicate<Object> predicate =
                rightOperandDataType.getPredicateByCommonOperation(commonOperation, rightOperand.value());

        return row -> row.entrySet()
                .stream()
                .filter(mapEntry -> mapEntry.getKey().equalsIgnoreCase(column))
                .map(Map.Entry::getValue)
                .anyMatch(predicate);
    }

    public static Predicate<Map<String, Object>> predicateOfConditions(Deque<LexemeEntity> conditionsInReverseOrder) throws ValidationException {
        if(conditionsInReverseOrder.isEmpty()) {
            return row -> true;
        }
        Iterator<LexemeEntity> conditionsInOrder = conditionsInReverseOrder.descendingIterator();
        Deque<Predicate<Map<String, Object>>> conditions = new ArrayDeque<>();

        while (conditionsInOrder.hasNext()) {
            LexemeEntity current = conditionsInOrder.next();
            if (current.type() == LexemeType.STRING_TYPE) {
                LexemeEntity operation = conditionsInOrder.next();
                LexemeEntity rightOperand = conditionsInOrder.next();
                Predicate<Map<String, Object>> stringPredicateEntry =
                        TableUtils.prepareOperationEntry(current, operation, rightOperand);

                conditions.push(stringPredicateEntry);
            }
            if (current.type() == LexemeType.LOGICAL_OPERATION_TYPE) {
                Predicate<Map<String, Object>> secondPredicate = conditions.poll();
                Predicate<Map<String, Object>> firstPredicate = conditions.poll();

                if ("AND".equalsIgnoreCase(current.value())) {
                    conditions.push(firstPredicate.and(secondPredicate));
                }
                if ("OR".equalsIgnoreCase(current.value())) {
                    conditions.push(firstPredicate.or(secondPredicate));
                }
            }

        }
        return conditions.poll();
    }
}
