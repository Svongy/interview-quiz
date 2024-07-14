package org.iq;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RawDataReaderTest {

    @Test
    void getQuestionsDataSet_valid() {
        File raw_data = new File(System.getProperty("user.dir") + "/raw_data.csv");
        raw_data.delete();
        RawDataReader.getQuestionsDataSet();
        assertTrue(raw_data.delete());
    }
}
