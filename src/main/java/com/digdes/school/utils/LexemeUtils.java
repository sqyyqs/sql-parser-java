package com.digdes.school.utils;

import com.digdes.school.domain.LexemeEntity;
import com.digdes.school.exception.ValidationException;
import com.digdes.school.operation.CommonOperation;
import com.digdes.school.operation.LexemeType;
import com.digdes.school.operation.LogicalOperation;
import com.digdes.school.type.DataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class LexemeUtils {
    private static final Map<String, LexemeType> QUERY_OPERATION_LEXEME_TYPE = Map.of(
            "select", LexemeType.SELECT_QUERY_TYPE,
            "insert values", LexemeType.INSERT_QUERY_TYPE,
            "delete", LexemeType.DELETE_QUERY_TYPE,
            "update values ", LexemeType.UPDATE_QUERY_TYPE
    );

    private LexemeUtils() {
    }

    public static List<LexemeEntity> processQueryToLexemes(String query) throws ValidationException {
        query = query.trim();
        List<LexemeEntity> lexemeEntities = new ArrayList<>();

        LexemeEntity lexemeEntity = extractQueryOperationToken(query);
        if (lexemeEntity != null) {
            lexemeEntities.add(lexemeEntity);
        }
        LexemeEntity previousLexemeEntity = lexemeEntity;

        while (previousLexemeEntity != null) {
            query = query.substring(previousLexemeEntity.value().length()).trim();

            LexemeEntity currentLexemeEntity = extractTokenFromQuery(query);

            if (currentLexemeEntity != null) {
                lexemeEntities.add(currentLexemeEntity);
            }
            previousLexemeEntity = currentLexemeEntity;
        }

        if (query.length() > 0) {
            throw new ValidationException("Incorrect syntax of query: " + query);
        }

        return lexemeEntities;
    }

    private static LexemeEntity extractTokenFromQuery(String query) {
        LexemeEntity commaToken = extractCommaToken(query);
        if (commaToken != null) {
            return commaToken;
        }

        LexemeEntity stringToken = extractStringToken(query);
        if (stringToken != null) {
            return stringToken;
        }

        LexemeEntity logicalToken = extractLogicalToken(query);
        if (logicalToken != null) {
            return logicalToken;
        }

        LexemeEntity compareOperationToken = extractCommonOperationToken(query);
        if (compareOperationToken != null) {
            return compareOperationToken;
        }

        LexemeEntity booleanToken = extractBooleanToken(query);
        if (booleanToken != null) {
            return booleanToken;
        }

        LexemeEntity numberToken = extractNumberToken(query);
        if (numberToken != null) {
            return numberToken;
        }

        LexemeEntity nullToken = extractNullToken(query);
        if (nullToken != null) {
            return nullToken;
        }

        LexemeEntity openingParenthesisToken = extractOpeningParenthesisToken(query);
        if (openingParenthesisToken != null) {
            return openingParenthesisToken;
        }

        LexemeEntity closingParenthesisToken = extractClosingParenthesisToken(query);
        if (closingParenthesisToken != null) {
            return closingParenthesisToken;
        }

        return null;
    }

    private static LexemeEntity extractCommonOperationToken(String query) {
        CommonOperation commonOperation = CommonOperation.fromQuery(query);
        if (commonOperation != null) {
            return new LexemeEntity(LexemeType.COMMON_OPERATION_TYPE, commonOperation.getOperation());
        }
        return null;
    }

    private static LexemeEntity extractQueryOperationToken(String query) {
        for (String operation : QUERY_OPERATION_LEXEME_TYPE.keySet()) {
            if (OperationUtils.queryStartWithOperation(query, operation)) {
                return new LexemeEntity(QUERY_OPERATION_LEXEME_TYPE.get(operation), operation);
            }
        }
        return null;
    }

    private static LexemeEntity extractLogicalToken(String query) {
        LogicalOperation logicalOperation = LogicalOperation.fromQuery(query);
        if (logicalOperation != null) {
            return new LexemeEntity(LexemeType.LOGIC_OPERATION_TYPE, logicalOperation.getOperation().trim());
        }
        return null;
    }

    private static LexemeEntity extractCommaToken(String query) {
        if (query.startsWith(",")) {
            return new LexemeEntity(LexemeType.COMMA_TYPE, ",");
        }
        return null;
    }

    private static LexemeEntity extractOpeningParenthesisToken(String query) {
        if (query.startsWith("(")) {
            return new LexemeEntity(LexemeType.OPENING_PARENTHESIS_TYPE, "(");
        }
        return null;
    }

    private static LexemeEntity extractClosingParenthesisToken(String query) {
        if (query.startsWith(")")) {
            return new LexemeEntity(LexemeType.CLOSING_PARENTHESIS_TYPE, ")");
        }
        return null;
    }

    private static LexemeEntity extractStringToken(String query) {
        if (query.startsWith("'")) {
            int endColumnName = query.substring(1).indexOf("'");
            if (endColumnName != -1) {
                return new LexemeEntity(LexemeType.STRING_TYPE, query.substring(0, endColumnName + 2));
            }
        }
        return null;
    }

    private static LexemeEntity extractBooleanToken(String query) {
        if (query.startsWith(Boolean.FALSE.toString())) {
            return new LexemeEntity(LexemeType.BOOLEAN_TYPE, Boolean.FALSE.toString());
        }
        if (query.startsWith(Boolean.TRUE.toString())) {
            return new LexemeEntity(LexemeType.BOOLEAN_TYPE, Boolean.TRUE.toString());
        }
        return null;
    }

    private static LexemeEntity extractNullToken(String query) {
        if (query.startsWith(DataType.NULL_VALUE)) {
            return new LexemeEntity(LexemeType.NULL_TYPE, DataType.NULL_VALUE);
        }
        return null;
    }

    private static LexemeEntity extractNumberToken(String query) {
        if (query.length() != 0) {
            if (Character.isDigit(query.charAt(0))) {
                String potentialNumber = query.split("[ ,)]+")[0];
                if (NumberUtils.isNumeric(potentialNumber)) {
                    if (potentialNumber.contains(".")) {
                        return new LexemeEntity(LexemeType.DOUBLE_TYPE, potentialNumber);
                    }
                    return new LexemeEntity(LexemeType.LONG_TYPE, potentialNumber);
                }
            }
        }
        return null;
    }


}
