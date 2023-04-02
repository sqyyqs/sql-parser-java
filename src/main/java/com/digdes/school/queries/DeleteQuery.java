package com.digdes.school.queries;

import java.util.List;
import java.util.Map;

public class DeleteQuery extends Query {

    public DeleteQuery(String[][] conditions) {
        super(conditions, null);
    }

    @Override
    public List<Map<String, Object>> executeQuery(List<Map<String, Object>> data) {
        return null;
    }
}
