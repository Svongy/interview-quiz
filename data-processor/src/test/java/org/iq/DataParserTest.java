package org.iq;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataParserTest {

    @Test
    void parseQuestions_valid() {
        RawDataReader.getQuestionsDataSet();
        List<Question> questions = DataParser.parseQuestions();
        assertFalse(questions.isEmpty());
    }

    @Test
    void parseQuestions_invalid() {
        boolean deleteFile = new File(System.getProperty("user.dir") + "/raw_data.csv").delete();
        List<Question> questions = DataParser.parseQuestions();
        assertTrue(questions.isEmpty());
    }
}
