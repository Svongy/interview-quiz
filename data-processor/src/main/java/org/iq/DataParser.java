package org.iq;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.java.Log;
import org.iq.utils.ConfigReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log
public class DataParser {
    private static final File RESULT_FILE_PATH = new File(ConfigReader.readProperty("OUT_FILE_PATH"));
    private static final CsvMapper csvMapper = new CsvMapper();
    private static final CsvSchema csvSchema = CsvSchema.emptySchema().withHeader().withNullValue("");

    public static List<Question> parseQuestions() {
        List<Question> questions = new ArrayList<>();

        try {
            MappingIterator<Question> questionsIterator =
                    csvMapper.readerFor(Question.class).with(csvSchema).readValues(RESULT_FILE_PATH);
            questions = questionsIterator.readAll();
        } catch (IOException e) {
            log.severe("Unable to parse questions from raw_data file. " + e.getMessage());
        }

        return questions;
    }
}
