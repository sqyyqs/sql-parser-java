package com.digdes.school.queries;

import java.util.List;
import java.util.Map;

public class UpdateQuery extends Query {

    public UpdateQuery(String[][] conditions, Map<String, String> columnToValue) {
        super(conditions, columnToValue);
    }

    @Override
    public List<Map<String, Object>> executeQuery(List<Map<String, Object>> params) {
        return null;
    }
}
