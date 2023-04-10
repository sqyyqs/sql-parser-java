package com.digdes.school.domain;

import com.digdes.school.exception.ValidationException;
import com.digdes.school.operation.LexemeType;
import com.digdes.school.utils.TableUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertQuery implements Query {
    @Override
    public List<Map<String, Object>> executeQuery(List<Map<String, Object>> table, List<LexemeEntity> lexemes) throws ValidationException {
        Map<String, Object> result = lexemeProcessing(lexemes);
        table.add(result);
        return table;
    }

    private static Map<String, Object> lexemeProcessing(List<LexemeEntity> lexemes) throws ValidationException {
        if (lexemes.size() < 4 || lexemes.size() % 4 != 0) {
            throw new ValidationException("Incorrect query.");
        }
        if (lexemes.get(0).type() != LexemeType.INSERT_QUERY_TYPE) {
            throw new ValidationException("Incorrect query.");
        }
        Map<String, Object> result = new HashMap<>();

        for (int idx = 0; idx < lexemes.size(); idx += 4) {
            LexemeEntity lexeme = lexemes.get(idx);
            if (idx > 0) {
                if (lexeme.type() != LexemeType.COMMA_TYPE) {
                    throw new ValidationException("");
                }
            }
            LexemeEntity leftOperand = lexemes.get(idx + 1);
            LexemeEntity operation = lexemes.get(idx + 2);
            LexemeEntity rightOperand = lexemes.get(idx + 3);

            Map.Entry<String, Object> prepared = TableUtils.prepareEntry(leftOperand, operation, rightOperand);
            result.put(prepared.getKey(), prepared.getValue());
        }

        return result;
    }
}
