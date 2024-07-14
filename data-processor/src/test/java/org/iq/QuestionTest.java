package org.iq;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

    static LocalDate currentDate = LocalDate.now();

    static Stream<Arguments> question_true_result() {
        String questionTypeText = "Text";
        String questionTypeCode = "Code";
        String questionTypeSingle = "Single";
        String questionTypeMulti = "Multi";

        String questionBoldOpen = "[*question1";
        String questionBoldClose = "question1*]";
        String questionItalicOpen = "ques/*tion1";
        String questionItalicClose = "ques*/tion1";

        String topic = "mock";

        String answer = "mock";

        String optionA = "mock";

        String today = currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String yesterday = currentDate.minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String tomorrow = currentDate.plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String almostMonthAgo = currentDate.minusDays(30).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String seen = "true";

        return Stream.of(
                Arguments.of(questionTypeText, questionBoldOpen, topic, answer, null, today, seen),
                Arguments.of(questionTypeCode, questionBoldClose, topic, answer, null, yesterday, seen),
                Arguments.of(questionTypeSingle, questionItalicOpen, topic, answer, optionA, tomorrow, seen),
                Arguments.of(questionTypeMulti, questionItalicClose, topic, answer, optionA, almostMonthAgo, seen)
        );
    }

    static Stream<Arguments> question_false_result() {
        String questionInvalidBold = "[ *question1//";
        String questionBoldClose = "question1//*";

        String emptyDate = "";
        String monthAgo = currentDate.minusDays(31).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return Stream.of(
                Arguments.of(questionInvalidBold, monthAgo),
                Arguments.of(questionBoldClose, emptyDate)
        );
    }

    static Stream<Arguments> question_disclaimer_exists() {
        String questionTypeCode = "Code";
        String questionTypeText = "Text";
        String questionTypeSingle = "Single";
        String questionTypeMulti = "Multi";

        String behavioralTopic = "behavioral";
        String BehavioralTopic = "Behavioral";
        String mockTopic = "mock";

        return Stream.of(
                Arguments.of(questionTypeText, behavioralTopic),
                Arguments.of(questionTypeSingle, BehavioralTopic),
                Arguments.of(questionTypeMulti, behavioralTopic),
                Arguments.of(questionTypeCode, mockTopic)
        );
    }

    static Stream<Arguments> question_disclaimer_not_exists() {
        String questionTypeText = "Text";
        String questionTypeSingle = "Single";
        String questionTypeMulti = "Multi";

        String mockTopic = "mock";

        return Stream.of(
                Arguments.of(questionTypeText, mockTopic),
                Arguments.of(questionTypeSingle, mockTopic),
                Arguments.of(questionTypeMulti, mockTopic)
        );
    }

    @ParameterizedTest
    @MethodSource("question_true_result")
    void question_generate_true(String type,
                                String questionText,
                                String topic,
                                String answer,
                                String a,
                                String date,
                                String seen) {
        Question question = new Question();
        question.setType(type);
        question.setQuestion(questionText);
        question.setTopic(topic);
        question.setAnswer(answer);
        question.setA(a);
        question.setDate(date);
        question.setSeen(seen);

        assertTrue(question.isNew());
        assertTrue(question.getQuestion().contains("span"));
        assertTrue(question.isValid());
        assertTrue(question.isSeen());
    }

    @ParameterizedTest
    @MethodSource("question_false_result")
    void question_generate_valid(String questionText, String topic) {
        Question question = new Question();
        question.setQuestion(questionText);
        question.setTopic(topic);

        assertFalse(question.isNew());
        assertFalse(question.getQuestion().contains("span"));
        assertFalse(question.isValid());
        assertFalse(question.isSeen());
    }

    @ParameterizedTest
    @MethodSource("question_disclaimer_exists")
    void question_check_disclaimer_exists(String type, String topic) {
        Question question = new Question();
        question.setType(type);
        question.setTopic(topic);

        question.setDisclaimer();

        assertFalse(question.getDisclaimer().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("question_disclaimer_not_exists")
    void question_check_disclaimer_not_exists(String type, String topic) {
        Question question = new Question();
        question.setType(type);
        question.setTopic(topic);

        question.setDisclaimer();

        assertNull(question.getDisclaimer());
    }



}
