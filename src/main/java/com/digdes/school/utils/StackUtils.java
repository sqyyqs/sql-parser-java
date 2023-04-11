package com.digdes.school.utils;

import com.digdes.school.domain.LexemeEntity;
import com.digdes.school.operation.LexemeType;
import com.digdes.school.operation.LogicalOperation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class StackUtils {
    private static final Map<LogicalOperation, Integer> LOGICAL_OPERATION_PRIORITY = Map.of(
            LogicalOperation.AND, 1,
            LogicalOperation.OR, 0
    );
    private static final Set<LexemeType> DATA_TYPES = Set.of(
            LexemeType.LONG_TYPE,
            LexemeType.BOOLEAN_TYPE,
            LexemeType.DOUBLE_TYPE,
            LexemeType.STRING_TYPE
    );

    private StackUtils() {
    }

    public static Deque<LexemeEntity> sortStationAlgorithm(List<LexemeEntity> lexemes) {
        Deque<LexemeEntity> operators = new ArrayDeque<>();
        Deque<LexemeEntity> result = new ArrayDeque<>();
        for (LexemeEntity lexeme : lexemes) {
            if (DATA_TYPES.contains(lexeme.type()) || (lexeme.type() == LexemeType.COMMON_OPERATION_TYPE)) {
                result.push(lexeme);
            }
            if (lexeme.type() == LexemeType.LOGICAL_OPERATION_TYPE && !"where".equals(lexeme.value())) {
                if (operators.isEmpty()) {
                    operators.push(lexeme);
                } else {
                    while (!operators.isEmpty() && operators.peek().type() != LexemeType.OPENING_PARENTHESIS_TYPE &&
                            LOGICAL_OPERATION_PRIORITY.get(LogicalOperation.of(operators.peek().value())) >
                                    LOGICAL_OPERATION_PRIORITY.get(LogicalOperation.of(lexeme.value()))) {
                        result.push(operators.poll());
                    }
                    operators.push(lexeme);
                }
            }
            if (lexeme.type() == LexemeType.OPENING_PARENTHESIS_TYPE) {
                operators.push(lexeme);
            }

            if (lexeme.type() == LexemeType.CLOSING_PARENTHESIS_TYPE) {
                while (operators.peek().type() != LexemeType.OPENING_PARENTHESIS_TYPE) {
                    result.push(operators.poll());
                }
                operators.poll();
            }
        }
        operators.forEach(result::push);
        return result;
    }
}
