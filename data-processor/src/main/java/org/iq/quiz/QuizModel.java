package org.iq.quiz;

import lombok.Getter;
import lombok.extern.java.Log;
import org.iq.DataParser;
import org.iq.Question;
import org.iq.RawDataReader;
import org.iq.enums.Competency;
import org.iq.enums.QuestionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Getter
public class QuizModel {
    private List<Question> questions;
    private boolean isDataLoaded = false;
    private final String[] questionsLimit = {"ALL", "1", "5", "10", "25"};

    public void loadData() {
        RawDataReader.getQuestionsDataSet();
    }

    public void generateQuestionsList() {
        this.questions = DataParser.parseQuestions();
        this.isDataLoaded = true;
    }

    public List<Question> generateQuiz(List<String> topics,
                                       List<String> tags,
                                       List<String> competencies,
                                       String limit) {
        List<Question> topicsFiltered = filterByTopic(topics);
        List<Question> tagsFiltered = filterByTags(tags, topicsFiltered);
        List<Question> competencyFiltered = filterByCompetency(competencies, tagsFiltered);

        Collections.shuffle(competencyFiltered);

        List<Question> limitedQuestions = limitQuestions(limit, competencyFiltered);

        return limitedQuestions;
    }

    private List<Question> limitQuestions(String limit, List<Question> questions) {
        if (!limit.equals("ALL")) {
            return questions.stream().limit(Integer.parseInt(limit)).toList();
        }

        return questions;
    }

    public List<Question> getQuestionsOfSpecificType(List<Question> questions, QuestionType questionType) {
        return questions.stream()
                .filter(question -> question.getType().equals(questionType))
                .collect(Collectors.toList());

    }

    public List<String> getDistinctTopics() {
        return questions.stream()
                .map(Question::getTopic)
                .distinct()
                .sorted()
                .toList();
    }

    public List<String> getDistinctTags() {
        return questions.stream()
                .flatMap(question -> question.getTags().stream())
                .distinct()
                .sorted()
                .toList();
    }

    public List<Competency> getDistinctCompetencies() {
        return questions.stream()
                .map(Question::getCompetency)
                .distinct()
                .sorted()
                .toList();
    }

    private List<Question> filterByCompetency(List<String> competencies, List<Question> tagsFiltered) {
        List<Question> competencyFiltered = new ArrayList<>();
        if (competencies != null) {
            for (String competency : competencies) {
                competencyFiltered.addAll(tagsFiltered.stream()
                        .filter(question -> question.getCompetency().name().equals(competency))
                        .toList());
            }
        } else {
            competencyFiltered.addAll(tagsFiltered);
        }
        return competencyFiltered;
    }

    private List<Question> filterByTags(List<String> tags, List<Question> topicsFiltered) {
        List<Question> tagsFiltered = new ArrayList<>();
        if (tags != null) {
            for (String tag : tags) {
                tagsFiltered.addAll(topicsFiltered.stream()
                        .filter(question -> question.getTags().contains(tag))
                        .toList());
            }
        } else {
            tagsFiltered.addAll(topicsFiltered);
        }
        return tagsFiltered;
    }

    private List<Question> filterByTopic(List<String> topics) {
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
