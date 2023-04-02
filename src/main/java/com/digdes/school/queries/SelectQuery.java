package com.digdes.school.queries;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SelectQuery extends Query {

    public SelectQuery(String[][] conditions) {
        super(conditions, null);
    }

    @Override
    public List<Map<String, Object>> executeQuery(List<Map<String, Object>> data) {
        return data
                .stream()
                .filter(this::checkForConditions)
                .toList();
    }

    private boolean checkForConditions(Map<String, Object> row) {
        boolean res = false;
        boolean temp = true;
        for (String[] conditionsByAnd : getConditions()) {
            for (String condition : conditionsByAnd) {
                Condition con = Condition.getCondition(condition);
                String[] values = condition.split(con.getConditionString());

                values[0] = values[0].strip();
                values[0] = values[0].substring(1, values[0].length() - 1);// убираем лишние пробелы и '' из значения

                String getResult = row
                        .keySet()
                        .stream()
                        .filter(x -> x.equalsIgnoreCase(values[0]))
                        .map(x -> row.get(x).toString())
                        .findAny()
                        .get();

                temp = evaluateCondition(getResult, values[1].strip(), con);
                if (!temp) {
                    break;
                }
            }
            res = temp;
            if (res) {
                break;
            }
            temp = true;
        }
        return res;
    }



    @Override
    public String toString() {
        return "SelectQuery{"
                + "Conditions: " + Arrays.deepToString(getConditions())
                + "}";
    }
}
