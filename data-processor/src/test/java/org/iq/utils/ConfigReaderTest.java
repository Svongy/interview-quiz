package org.iq.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ConfigReaderTest {

    @ParameterizedTest
    @ValueSource(strings = {"GOOGLE_SHEET_ID", "GOOGLE_SHEET_URL", "DATA_FETCH_FLAG"})
    void readProperty_validInput(String key) {
        String property = ConfigReader.readProperty(key);
        assertNotNull(property);
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "", "GOOGLE_SHEET_ID1"})
    void readProperty_invalidInput(String key) {
        String property = ConfigReader.readProperty(key);
        assertNull(property);
    }
}
