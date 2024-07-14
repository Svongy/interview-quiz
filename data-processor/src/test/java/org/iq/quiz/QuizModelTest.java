package org.iq.quiz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class QuizModelTest {

    QuizModel quizModel;

    @BeforeEach
    void setup() {
        boolean deleteFile = new File(System.getProperty("user.dir") + "/raw_data.csv").delete();
        quizModel = new QuizModel();
    }


    static Stream<Arguments> filters_valid() {
        List<String> singleTopic = List.of("Testing theory");
        List<String> singleTag = List.of("Performance");
        List<String> singleCompetency = List.of("Junior");

        List<String> topics = List.of("Testing theory", "Java");
        List<String> tags = List.of("Performance", "Java-core");
        List<String> competencies = List.of("Junior", "Senior");

        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of(singleTopic, null, null),
                Arguments.of(null, singleTag, null),
                Arguments.of(null, null, singleCompetency),
                Arguments.of(singleTopic, singleTag, singleCompetency),
                Arguments.of(topics, tags, competencies)
        );
    }

    static Stream<Arguments> filters_invalid() {
        List<String> emptyTopics = List.of("");
        List<String> emptyTags = List.of("");
        List<String> emptyCompetencies = List.of("");

        List<String> topics = List.of("123");
        List<String> tags = List.of("123");
        List<String> competencies = List.of("123");

        return Stream.of(
                Arguments.of(emptyTopics, emptyTags, emptyCompetencies),
                Arguments.of(topics, null, null),
                Arguments.of(null, tags, null),
                Arguments.of(null, null, competencies),
                Arguments.of(topics, tags, competencies)
        );
    }

    @Test
    void quizModel_defaults() {
        assertFalse(quizModel.isDataLoaded());
        assertNull(quizModel.getRawQuestions());
        assertNull(quizModel.getReadyQuestions());
        assertEquals(quizModel.getQuestionsLimit().length, 6);
    }

    @Test
    void generateQuestionsList_validDataSet() {
        quizModel.loadData();
        quizModel.generateQuestionsList();

        assertTrue(quizModel.isDataLoaded());
    }

    @Test
    void generateQuestionsList_invalidDataSet() {
        quizModel.generateQuestionsList();

        assertFalse(quizModel.isDataLoaded());
    }

    @ParameterizedTest
    @MethodSource("filters_valid")
    void generateQuiz_validInput(List<String> topics, List<String> tags, List<String> competencies) {
        quizModel.loadData();
        quizModel.generateQuestionsList();
        quizModel.generateQuiz(topics, tags, competencies);

        assertFalse(quizModel.getReadyQuestions().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("filters_invalid")
    void generateQuiz_invalidInput(List<String> topics, List<String> tags, List<String> competencies) {
        quizModel.loadData();
        quizModel.generateQuestionsList();
        quizModel.generateQuiz(topics, tags, competencies);

        assertTrue(quizModel.getReadyQuestions().isEmpty());
    }

    @Test
    void getDistinctTopicsTagsCompetencies_validInput() {
        quizModel.loadData();
        quizModel.generateQuestionsList();

        assertNotNull(quizModel.getDistinctTopics());
        assertNotNull(quizModel.getDistinctTags());
        assertNotNull(quizModel.getDistinctCompetencies());
    }

    @Test
    void getDistinctTopicsTagsCompetencies_invalidInput() {
        assertNull(quizModel.getDistinctTopics());
        assertNull(quizModel.getDistinctTags());
        assertNull(quizModel.getDistinctCompetencies());
    }
}
