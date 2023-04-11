package com.digdes.school;

import com.digdes.school.utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaSchoolStarter {
    private static final List<Map<String, Object>> data = new ArrayList<>();

    public JavaSchoolStarter() {
    }

    public List<Map<String, Object>> execute(String request) throws Exception {
        return QueryUtils.evaluateRequest(request, data);
    }

    public static void main(String[] args) throws Exception {
        JavaSchoolStarter jss = new JavaSchoolStarter();
        System.out.println(jss.execute("insert values 'lastName' = null"));
        System.out.println(jss.execute("insert values 'lastName' = null"));
        System.out.println(jss.execute("insert values 'lastName' = null"));
        System.out.println(jss.execute("update values 'lasTNAME' = 'dima'"));
        System.out.println(jss.execute("select where 'lastName' ilike '%IMA'"));
    }
}
