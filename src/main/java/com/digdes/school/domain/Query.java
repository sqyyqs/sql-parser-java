package com.digdes.school.domain;

import com.digdes.school.exception.ValidationException;

import java.util.List;
import java.util.Map;

public interface Query {
    List<Map<String, Object>> executeQuery(List<Map<String, Object>> table, List<LexemeEntity> lexemes) throws ValidationException;
}
