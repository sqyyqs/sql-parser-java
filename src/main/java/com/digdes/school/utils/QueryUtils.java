package com.digdes.school.utils;

import com.digdes.school.domain.DeleteQuery;
import com.digdes.school.domain.InsertQuery;
import com.digdes.school.domain.LexemeEntity;
import com.digdes.school.domain.Query;
import com.digdes.school.domain.SelectQuery;
import com.digdes.school.domain.UpdateQuery;
import com.digdes.school.exception.ValidationException;

import java.util.List;
import java.util.Map;

public final class QueryUtils {
    private QueryUtils() {
    }

    public static List<Map<String, Object>> evaluateRequest(String request, List<Map<String, Object>> table) throws ValidationException {
        if(request == null) {
            throw new ValidationException("Query can't be a null.");
        }
        List<LexemeEntity> lexemes = LexemeUtils.processQueryToLexemes(request);
        if (lexemes.isEmpty()) {
            throw new ValidationException("Query is empty.");
        }
        Query query;
        switch (lexemes.get(0).type()) {
            case INSERT_QUERY_TYPE -> query = new InsertQuery();
            case SELECT_QUERY_TYPE -> query = new SelectQuery();
            case UPDATE_QUERY_TYPE -> query = new UpdateQuery();
            case DELETE_QUERY_TYPE -> query = new DeleteQuery();
            default -> throw new ValidationException("Unknown query type: " + lexemes.get(0).value());
        }
        return query.executeQuery(table, lexemes);
    }
}
