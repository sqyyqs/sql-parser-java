package com.digdes.school.queries;

import java.util.Arrays;

public enum Condition {
    //Реализация зависит от порядка операций. Плохо
    LTE("<="),
    GTE(">="),
    NE("!="),
    LT("<"),
    GT(">"),
    EQ("="),
    LIKE(" like "),
    ILIKE(" ilike ");

    private final String condition;

    Condition(String condition) {
        this.condition = condition;
    }

    public String getConditionString() {
        return condition;
    }

    public static Condition getCondition(String condition) {
        return Arrays.stream(Condition.values())
                .filter(x -> condition.contains(x.getConditionString()))
                .findAny()
                .orElse(null);
    }



}
