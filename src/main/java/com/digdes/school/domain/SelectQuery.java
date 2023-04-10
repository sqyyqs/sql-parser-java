package com.digdes.school.domain;

import com.digdes.school.exception.ValidationException;
import com.digdes.school.utils.StackUtils;
import com.digdes.school.utils.TableUtils;

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class SelectQuery implements Query {
    @Override
    public List<Map<String, Object>> executeQuery(List<Map<String, Object>> table, List<LexemeEntity> lexemes) throws ValidationException {
        Deque<LexemeEntity> conditionLexemesInRPN = StackUtils.sortStationAlgorithm(lexemes);
        Predicate<Map<String, Object>> objectPredicate = TableUtils.predicateOfMultipleConditions(conditionLexemesInRPN);
        return table.stream()
                .filter(objectPredicate)
                .toList();
    }

}
