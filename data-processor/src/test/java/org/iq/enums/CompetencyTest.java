package org.iq.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompetencyTest {

    @Test
    void competency_size() {
        assertEquals(Competency.values().length, 3);
    }

    @ParameterizedTest
    @ValueSource(strings = {"junior", "JUNIOR", "Junior", "jUNIor"})
    void createJuniorCompetency_fromString_validString(String competency) {
        assertEquals(Competency.Junior, Competency.fromString(competency));
    }

    @ParameterizedTest
    @ValueSource(strings = {"middle", "MIDDLE", "Middle", "midDle"})
    void createMiddleCompetency_fromString_validString(String competency) {
        assertEquals(Competency.Middle, Competency.fromString(competency));
    }

    @ParameterizedTest
    @ValueSource(strings = {"senior", "SENIOR", "Senior", "sEnIoR"})
    void createSeniorCompetency_fromString_validString(String competency) {
        assertEquals(Competency.Senior, Competency.fromString(competency));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "JUNIO", "0000", "null"})
    void createJuniorCompetency_fromString_invalidString(String competency) {
        assertEquals(Competency.Junior, Competency.fromString(competency));
    }
}
