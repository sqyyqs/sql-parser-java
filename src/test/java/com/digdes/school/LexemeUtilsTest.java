package com.digdes.school;

import com.digdes.school.domain.LexemeEntity;
import com.digdes.school.exception.ValidationException;
import com.digdes.school.operation.LexemeType;
import com.digdes.school.utils.LexemeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LexemeUtilsTest {
    @Test
    void testProcessQueryToLexemes() throws Exception {
        String query = "select where 'age' > 30 and 'name' = 'John Smith'";
        List<LexemeEntity> lexemes = LexemeUtils.processQueryToLexemes(query);

        Assertions.assertEquals(new LexemeEntity(LexemeType.SELECT_QUERY_TYPE, "select"), lexemes.get(0));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LOGICAL_OPERATION_TYPE, "where"), lexemes.get(1));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'age'"), lexemes.get(2));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, ">"), lexemes.get(3));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LONG_TYPE, "30"), lexemes.get(4));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LOGICAL_OPERATION_TYPE, "and"), lexemes.get(5));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'name'"), lexemes.get(6));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, "="), lexemes.get(7));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'John Smith'"), lexemes.get(8));
    }

    @Test
    void testProcessQueryToLexemesWithWhitespaces() throws Exception {
        String query = "select       where     'age'       >      30 and       'name'    =     'John Smith'     ";
        List<LexemeEntity> lexemes = LexemeUtils.processQueryToLexemes(query);

        Assertions.assertEquals(new LexemeEntity(LexemeType.SELECT_QUERY_TYPE, "select"), lexemes.get(0));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LOGICAL_OPERATION_TYPE, "where"), lexemes.get(1));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'age'"), lexemes.get(2));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, ">"), lexemes.get(3));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LONG_TYPE, "30"), lexemes.get(4));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LOGICAL_OPERATION_TYPE, "and"), lexemes.get(5));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'name'"), lexemes.get(6));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, "="), lexemes.get(7));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'John Smith'"), lexemes.get(8));
    }

    @Test
    void testProcessQueryToLexemesWithMinimalSpaces() throws Exception {
        String query = "select where 'age'>30 and 'name'='John Smith'";
        List<LexemeEntity> lexemes = LexemeUtils.processQueryToLexemes(query);

        Assertions.assertEquals(new LexemeEntity(LexemeType.SELECT_QUERY_TYPE, "select"), lexemes.get(0));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LOGICAL_OPERATION_TYPE, "where"), lexemes.get(1));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'age'"), lexemes.get(2));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, ">"), lexemes.get(3));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LONG_TYPE, "30"), lexemes.get(4));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LOGICAL_OPERATION_TYPE, "and"), lexemes.get(5));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'name'"), lexemes.get(6));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, "="), lexemes.get(7));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'John Smith'"), lexemes.get(8));
    }

    @Test
    void testProcessQueryToLexemesWithLetterCaseMixing() throws Exception {
        String query = "sElEcT wHeRE 'aGe'>30 AND 'nAme'='John Smith'";
        List<LexemeEntity> lexemes = LexemeUtils.processQueryToLexemes(query);

        Assertions.assertEquals(new LexemeEntity(LexemeType.SELECT_QUERY_TYPE, "select"), lexemes.get(0));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LOGICAL_OPERATION_TYPE, "where"), lexemes.get(1));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'aGe'"), lexemes.get(2));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, ">"), lexemes.get(3));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LONG_TYPE, "30"), lexemes.get(4));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LOGICAL_OPERATION_TYPE, "and"), lexemes.get(5));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'nAme'"), lexemes.get(6));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, "="), lexemes.get(7));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'John Smith'"), lexemes.get(8));
    }

    @Test
    void testProcessQueryToLexemesWithParentheses() throws Exception {
        String query = "select where ('age'>30 OR 'name'='John Smith') AND ('cost' = 2.34 OR 'lastName' ilike '%ame')";
        List<LexemeEntity> lexemes = LexemeUtils.processQueryToLexemes(query);

        Assertions.assertEquals(new LexemeEntity(LexemeType.SELECT_QUERY_TYPE, "select"), lexemes.get(0));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LOGICAL_OPERATION_TYPE, "where"), lexemes.get(1));

        Assertions.assertEquals(new LexemeEntity(LexemeType.OPENING_PARENTHESIS_TYPE, "("), lexemes.get(2));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'age'"), lexemes.get(3));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, ">"), lexemes.get(4));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LONG_TYPE, "30"), lexemes.get(5));

        Assertions.assertEquals(new LexemeEntity(LexemeType.LOGICAL_OPERATION_TYPE, "or"), lexemes.get(6));

        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'name'"), lexemes.get(7));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, "="), lexemes.get(8));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'John Smith'"), lexemes.get(9));
        Assertions.assertEquals(new LexemeEntity(LexemeType.CLOSING_PARENTHESIS_TYPE, ")"), lexemes.get(10));

        Assertions.assertEquals(new LexemeEntity(LexemeType.LOGICAL_OPERATION_TYPE, "and"), lexemes.get(11));

        Assertions.assertEquals(new LexemeEntity(LexemeType.OPENING_PARENTHESIS_TYPE, "("), lexemes.get(12));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'cost'"), lexemes.get(13));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, "="), lexemes.get(14));
        Assertions.assertEquals(new LexemeEntity(LexemeType.DOUBLE_TYPE, "2.34"), lexemes.get(15));

        Assertions.assertEquals(new LexemeEntity(LexemeType.LOGICAL_OPERATION_TYPE, "or"), lexemes.get(16));

        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'lastName'"), lexemes.get(17));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, "ilike"), lexemes.get(18));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'%ame'"), lexemes.get(19));
        Assertions.assertEquals(new LexemeEntity(LexemeType.CLOSING_PARENTHESIS_TYPE, ")"), lexemes.get(20));

    }

    @Test
    void testProcessQueryInsertToLexemes() throws Exception {
        String query = "insert VALUES 'lastName' = 'Dmitriy', 'cost' = 2.55, 'id' = 123123123";
        List<LexemeEntity> lexemes = LexemeUtils.processQueryToLexemes(query);

        Assertions.assertEquals(new LexemeEntity(LexemeType.INSERT_QUERY_TYPE, "insert"), lexemes.get(0));
        Assertions.assertEquals(new LexemeEntity(LexemeType.VALUES_TYPE, "values"), lexemes.get(1));

        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'lastName'"), lexemes.get(2));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, "="), lexemes.get(3));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'Dmitriy'"), lexemes.get(4));

        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMA_TYPE, ","), lexemes.get(5));

        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'cost'"), lexemes.get(6));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, "="), lexemes.get(7));
        Assertions.assertEquals(new LexemeEntity(LexemeType.DOUBLE_TYPE, "2.55"), lexemes.get(8));

        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMA_TYPE, ","), lexemes.get(9));

        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'id'"), lexemes.get(10));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, "="), lexemes.get(11));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LONG_TYPE, "123123123"), lexemes.get(12));
    }

    @Test
    void testProcessQueryUpdateToLexemes() throws Exception {
        String query = "upDATe   VALUES 'lastName' = 'Dmitriy', 'cost' = 2.55, 'id' = 123123123 wHeRE ('id' > 1 or 'cost' < 2.55) and 'lastName' like '%e'";
        List<LexemeEntity> lexemes = LexemeUtils.processQueryToLexemes(query);

        Assertions.assertEquals(new LexemeEntity(LexemeType.UPDATE_QUERY_TYPE, "update"), lexemes.get(0));
        Assertions.assertEquals(new LexemeEntity(LexemeType.VALUES_TYPE, "values"), lexemes.get(1));


        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'lastName'"), lexemes.get(2));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, "="), lexemes.get(3));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'Dmitriy'"), lexemes.get(4));

        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMA_TYPE, ","), lexemes.get(5));

        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'cost'"), lexemes.get(6));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, "="), lexemes.get(7));
        Assertions.assertEquals(new LexemeEntity(LexemeType.DOUBLE_TYPE, "2.55"), lexemes.get(8));

        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMA_TYPE, ","), lexemes.get(9));

        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'id'"), lexemes.get(10));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, "="), lexemes.get(11));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LONG_TYPE, "123123123"), lexemes.get(12));

        Assertions.assertEquals(new LexemeEntity(LexemeType.LOGICAL_OPERATION_TYPE, "where"), lexemes.get(13));

        Assertions.assertEquals(new LexemeEntity(LexemeType.OPENING_PARENTHESIS_TYPE, "("), lexemes.get(14));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'id'"), lexemes.get(15));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, ">"), lexemes.get(16));
        Assertions.assertEquals(new LexemeEntity(LexemeType.LONG_TYPE, "1"), lexemes.get(17));

        Assertions.assertEquals(new LexemeEntity(LexemeType.LOGICAL_OPERATION_TYPE, "or"), lexemes.get(18));

        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'cost'"), lexemes.get(19));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, "<"), lexemes.get(20));
        Assertions.assertEquals(new LexemeEntity(LexemeType.DOUBLE_TYPE, "2.55"), lexemes.get(21));
        Assertions.assertEquals(new LexemeEntity(LexemeType.CLOSING_PARENTHESIS_TYPE, ")"), lexemes.get(22));

        Assertions.assertEquals(new LexemeEntity(LexemeType.LOGICAL_OPERATION_TYPE, "and"), lexemes.get(23));

        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'lastName'"), lexemes.get(24));
        Assertions.assertEquals(new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, "like"), lexemes.get(25));
        Assertions.assertEquals(new LexemeEntity(LexemeType.STRING_TYPE, "'%e'"), lexemes.get(26));
    }

    @Test
    void testProcessQueryDeleteToLexemes() throws Exception {
        String query = "delete";
        List<LexemeEntity> lexemes = LexemeUtils.processQueryToLexemes(query);

        Assertions.assertEquals(new LexemeEntity(LexemeType.DELETE_QUERY_TYPE, "delete"), lexemes.get(0));
    }

    @Test
    void testProcessQueryWithException() throws Exception {
        String query = "deletee sdfjgi djfgopisdfj[=p";
        Assertions.assertThrows(ValidationException.class, () -> LexemeUtils.processQueryToLexemes(query));

        String query2 = "delete from table where id eq 223";
        Assertions.assertThrows(ValidationException.class, () -> LexemeUtils.processQueryToLexemes(query2));

        String query3 = null;
        Assertions.assertThrows(NullPointerException.class, () -> LexemeUtils.processQueryToLexemes(query3));

        String query4 = "insert  'cost' = 2.34 where 'cost'=32.0";
        Assertions.assertThrows(ValidationException.class, () -> LexemeUtils.processQueryToLexemes(query4));
    }

}
