package org.iq.quiz;

import lombok.Getter;
import org.iq.DataParser;
import org.iq.Question;
import org.iq.RawDataReader;
import org.iq.enums.Competency;
import org.iq.enums.QuestionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class QuizModel {
    private boolean isDataLoaded = false;
    private List<Question> rawQuestions;
    private List<Question> readyQuestions;
    private final String[] questionsLimit = {"ALL", "1", "5", "10", "NEW", "SEEN ON INTERVIEW"};

    public void loadData() {
        RawDataReader.getQuestionsDataSet();
    }

    public void generateQuestionsList() {
        this.rawQuestions = DataParser.parseQuestions();
        this.isDataLoaded = !this.rawQuestions.isEmpty();
    }

    public void generateQuiz(List<String> topics,
                                       List<String> tags,
                                       List<String> competencies) {
        List<Question> topicsFiltered = filterByTopic(topics, rawQuestions);
        List<Question> tagsFiltered = filterByTags(tags, topicsFiltered);
        List<Question> competencyFiltered = filterByCompetency(competencies, tagsFiltered);

        Collections.shuffle(competencyFiltered);

        this.readyQuestions = competencyFiltered;
    }

    public List<String> getDistinctTopics() {
        return rawQuestions != null ? rawQuestions.stream()
                .map(Question::getTopic)
                .distinct()
                .sorted()
                .toList() : null;
    }

    public List<String> getDistinctTags() {
        return rawQuestions != null ? rawQuestions.stream()
                .flatMap(question -> question.getTags().stream())
                .distinct()
                .sorted()
                .toList() : null;
    }

    public List<Competency> getDistinctCompetencies() {
        return rawQuestions != null ? rawQuestions.stream()
                .map(Question::getCompetency)
                .distinct()
                .sorted()
                .toList() : null;
    }

    private List<Question> filterByCompetency(List<String> competencies, List<Question> questions) {
        List<Question> competencyFiltered = new ArrayList<>();
        if (competencies != null) {
            for (String competency : competencies) {
                competencyFiltered.addAll(questions.stream()
                        .filter(question -> question.getCompetency().name().equals(competency))
                        .toList());
            }
        } else {
            competencyFiltered.addAll(questions);
        }
        return competencyFiltered;
    }

    private List<Question> filterByTags(List<String> tags, List<Question> questions) {
        List<Question> tagsFiltered = new ArrayList<>();
        if (tags != null) {
            for (String tag : tags) {
                tagsFiltered.addAll(questions.stream()
                        .filter(question -> question.getTags().contains(tag))
                        .toList());
            }
        } else {
            tagsFiltered.addAll(questions);
        }
        return tagsFiltered;
    }

    private List<Question> filterByTopic(List<String> topics, List<Question> questions) {
        List<Question> topicsFiltered = new ArrayList<>();
        if (topics != null) {
            for (String topic : topics) {
               topicsFiltered.addAll(questions.stream()
                       .filter(question -> question.getTopic().equals(topic))
                       .toList());
            }
        } else {
            topicsFiltered.addAll(questions);
        }
        return topicsFiltered;
    }
}
