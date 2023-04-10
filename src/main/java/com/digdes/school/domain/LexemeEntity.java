package com.digdes.school.domain;

import com.digdes.school.operation.LexemeType;

public record LexemeEntity(LexemeType type, String value) {
    @Override
    public String toString() {
        return "LexemeEntity{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
