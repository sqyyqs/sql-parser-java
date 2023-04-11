package com.digdes.school.domain;

import com.digdes.school.exception.ValidationException;
import com.digdes.school.operation.LexemeType;
import com.digdes.school.utils.StackUtils;
import com.digdes.school.utils.TableUtils;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class UpdateQuery implements Query {
    @Override
    public List<Map<String, Object>> executeQuery(List<Map<String, Object>> table, List<LexemeEntity> lexemes) throws ValidationException {
        Map<String, Object> updatedValues = lexemeProcessing(lexemes);
        List<LexemeEntity> conditionLexemes = lexemes.stream().dropWhile(lexeme -> !"where".equals(lexeme.value())).toList();
        Predicate<Map<String, Object>> rowPredicate = row -> true;
        Deque<LexemeEntity> conditionLexemesInRPN = StackUtils.sortStationAlgorithm(conditionLexemes);

        if (!conditionLexemesInRPN.isEmpty()) {
            rowPredicate = TableUtils.predicateOfConditions(conditionLexemesInRPN);
        }

        List<Map<String, Object>> affectedRows = new ArrayList<>();
        table.stream()
                .filter(rowPredicate)
                .forEach(row -> {
                    affectedRows.add(new HashMap<>(row));
                    row.putAll(updatedValues);
                });
        return affectedRows;
    }

    private static Map<String, Object> lexemeProcessing(List<LexemeEntity> lexemes) throws ValidationException {
        Map<String, Object> updatedValues = new HashMap<>();
        if (lexemes.size() < 5 || lexemes.size() % 4 != 1) {
            throw new ValidationException("Incorrect query.");
        }
        if (lexemes.get(0).type() != LexemeType.UPDATE_QUERY_TYPE || lexemes.get(1).type() != LexemeType.VALUES_TYPE) {
            throw new ValidationException("Incorrect query.");
        }

        for (int idx = 1; idx < lexemes.size(); idx += 4) {
            LexemeEntity lexeme = lexemes.get(idx);
            if (idx > 1) {
                if (lexeme.type() == LexemeType.LOGICAL_OPERATION_TYPE) {
                    return updatedValues;
                }
            }
            LexemeEntity leftOperand = lexemes.get(idx + 1);
            LexemeEntity operation = lexemes.get(idx + 2);
            LexemeEntity rightOperand = lexemes.get(idx + 3);

            Map.Entry<String, Object> prepared = TableUtils.prepareEntry(leftOperand, operation, rightOperand);
            updatedValues.put(prepared.getKey(), prepared.getValue());
        }
        return updatedValues;
    }
}
