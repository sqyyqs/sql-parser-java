package com.digdes.school.queries;

import java.util.List;
import java.util.Map;

public abstract class Query {
    private final String[][] conditions;
    private final Map<String, String> columnToValue;

    public Query(String[][] conditions, Map<String, String> columnToValue) {
        this.conditions = conditions;
        this.columnToValue = columnToValue;
    }

    public abstract List<Map<String, Object>> executeQuery(List<Map<String, Object>> data);

    public String[][] getConditions() {
        return conditions;
    }

    public Map<String, String> getColumnToValue() {
        return columnToValue;
    }

    protected static boolean evaluateCondition(String value1, String value2, Condition condition) {
        if (value2.startsWith("'") && value2.endsWith("'")) {
            value2 = value2.substring(1, value2.length() - 1);
        }
        return switch (condition) {
            case LT -> Double.parseDouble(value1) < Double.parseDouble(value2);
            case LTE -> Double.parseDouble(value1) <= Double.parseDouble(value2);
            case GT -> Double.parseDouble(value1) > Double.parseDouble(value2);
            case GTE -> Double.parseDouble(value1) >= Double.parseDouble(value2);
            case LIKE -> false;
            case ILIKE -> false;
            case EQ -> value1.equals(value2);
            case NE -> !value1.equals(value2);
        };
    }
}
