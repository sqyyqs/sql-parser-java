package com.digdes.school;

import com.digdes.school.queries.Query;
import com.digdes.school.queries.QueryUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final List<Map<String, Object>> data;

    static {
        data = new ArrayList<>();

        Map<String, Object> row1 = new HashMap<>();
        row1.put("id", 6);
        row1.put("name", "dima");
        row1.put("smth", "not smth");
        data.add(row1);

        Map<String, Object> row2 = new HashMap<>();
        row2.put("id", 4);
        row2.put("name", "not dima");
        row2.put("smth", "smth");
        data.add(row2);

        Map<String, Object> row3 = new HashMap<>();
        row3.put("id", 1);
        row3.put("name", "123dima");
        row3.put("smth", "smth");
        data.add(row3);

        Map<String, Object> row4 = new HashMap<>();
        row4.put("id", 6);
        row4.put("name", "qwdqwddima");
        row4.put("smth", "not smth");
        data.add(row4);

        Map<String, Object> row5 = new HashMap<>();
        row5.put("id", 24);
        row5.put("name", "adasddima");
        row5.put("smth", "not smth");
        data.add(row5);

    }

    public static void main(String[] args) throws Exception {
        Query query = QueryUtils.buildQueryFromString("select where 'id' >=    6 AND 'name' =        'dima'     "); // row1
        Query querys = QueryUtils.buildQueryFromString("select where 'id'>=6 AND 'name'='dima'"); // row1
        Query query2 = QueryUtils.buildQueryFromString("select where 'id' >=    6 AND 'name' = 'dima' OR 'id' < 5 AND 'smth'=smth"); //row1, row2, row 3

        System.out.println(query.executeQuery(data));
        System.out.println("query 1 is built");
        System.out.println(querys.executeQuery(data));
        System.out.println("querys is built");
        System.out.println(query2.executeQuery(data));
        System.out.println("query 2 is built");
    }
}
