package org.iq.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionTypeTest {

    @Test
    void questionType_size() {
        assertEquals(QuestionType.values().length, 4);
    }

    @ParameterizedTest
    @ValueSource(strings = {"text", "TEXT", "Text", "TeXt"})
    void createTextQuestionType_fromString_ValidString(String type) {
        assertEquals(QuestionType.TEXT, QuestionType.fromString(type));
    }

    @ParameterizedTest
    @ValueSource(strings = {"code", "CODE", "Code", "COde"})
    void createCodeQuestionType_fromString_ValidString(String type) {
        assertEquals(QuestionType.CODE, QuestionType.fromString(type));
    }

    @ParameterizedTest
    @ValueSource(strings = {"single", "SINGLE", "Single", "SIngLE"})
    void createSingleQuestionType_fromString_ValidString(String type) {
        assertEquals(QuestionType.SINGLE, QuestionType.fromString(type));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Multi", "MULTI", "Multi", "multI"})
    void createMultiQuestionType_fromString_ValidString(String type) {
        assertEquals(QuestionType.MULTI, QuestionType.fromString(type));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "TE XT", "111", "null"})
    void createTextQuestionType_fromString_invalidString(String type) {
        assertEquals(QuestionType.TEXT, QuestionType.fromString(type));
    }
}
