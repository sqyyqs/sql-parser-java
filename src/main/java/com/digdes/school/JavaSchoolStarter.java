package com.digdes.school;

import com.digdes.school.utils.QueryUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaSchoolStarter {
    private static final List<Map<String, Object>> data = new ArrayList<>();

    static {
        Map<String, Object> w = new HashMap<>();
        w.put("lastName", "Dmitriy");
        w.put("age", 18);
        w.put("cost", 23.2);
        data.add(w);

        Map<String, Object> w2 = new HashMap<>();
        w2.put("lastName", "Dmitriy");
        w2.put("age", 19);
        w2.put("cost", 24.2);
        data.add(w2);

        Map<String, Object> w3 = new HashMap<>();
        w3.put("id", 1);
        w3.put("age", 19);
        w3.put("cost", 24.2);
        data.add(w3);
    }

    public JavaSchoolStarter() {
    }

    public List<Map<String, Object>> execute(String request) throws Exception {
        return QueryUtils.evaluateRequest(request, data);
    }

    public static void main(String[] args) throws Exception {
        JavaSchoolStarter jss = new JavaSchoolStarter();
        List<Map<String, Object>> execute = jss.execute("select where 'id' = 1");
        System.out.println(execute);
    }
}
