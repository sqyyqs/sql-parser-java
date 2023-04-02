package com.digdes.school.queries;

import java.util.Arrays;
import java.util.Map;


public class QueryUtils {
    private QueryUtils() {
        throw new RuntimeException();
    }

    public static Query buildQueryFromString(String query) throws Exception {
        String[][] conditions;
        Map<String, String> columnToValue;
        String queryType = query.split(" ")[0].toUpperCase().strip();

        if (!query.toUpperCase().contains(" WHERE ")) {
            conditions = null;
        } else {
            conditions = Arrays.stream(query
                            .split("(?i) WHERE ")[1]
                            .split(" OR "))
                    .map(x -> x.split(" AND "))
                    .toArray(String[][]::new);
        }
        if (query.toUpperCase().contains(" VALUES ")) {
            columnToValue = null;
            // INSERT, UPDATE
            // todo
        } else {
            columnToValue = null;
            // SELECT, DELETE
        }
        return switch (queryType) {
            case "SELECT" -> new SelectQuery(conditions);
            case "INSERT" -> new InsertQuery(conditions, columnToValue);
            case "UPDATE" -> new UpdateQuery(conditions, columnToValue);
            case "DELETE" -> new DeleteQuery(conditions);
            default -> throw new Exception("This type of query does not exist.");
        };
    }

}
