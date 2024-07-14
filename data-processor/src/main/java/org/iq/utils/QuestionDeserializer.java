package org.iq.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.iq.Question;
import org.iq.enums.QuestionType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDeserializer extends StdDeserializer<Question> {
    private final String QUESTION_TYPE_FIELD = "type";
    private final String QUESTION_FIELD = "question";
    private final String TOPIC_FIELD = "topic";
    private final String TAG_FIELDS = "tags";
    private final String COMPETENCY_FIELD = "competency";
    private final String A_FIELD = "a";
    private final String B_FIELD = "b";
    private final String C_FIELD = "c";
    private final String D_FIELD = "d";
    private final String ANSWER_FIELD = "answer";
    private final String SEEN_ON_INTERVIEW_FIELD = "seen";
    private final String DATE_FIELD = "date";

    public QuestionDeserializer() {
        this(null);
    }

    protected QuestionDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Question deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        System.out.println(node);

        String questionType = node.get(QUESTION_TYPE_FIELD).asText();
        String question = node.get(QUESTION_FIELD).asText();
        String topic = node.get(TOPIC_FIELD).asText();
        List<String> tags = new ArrayList<>();
        for (JsonNode optionNode : node.get(TAG_FIELDS)) {
            tags.add(optionNode.asText());
        }
        String competency = node.get(COMPETENCY_FIELD).asText();
        String a = node.get(A_FIELD).asText();
        String b = node.get(B_FIELD).asText();
        String c = node.get(C_FIELD).asText();
        String d = node.get(D_FIELD).asText();
        String answer = node.get(ANSWER_FIELD).asText();
        String seenOnInterview = node.get(SEEN_ON_INTERVIEW_FIELD).asText();
        String date = node.get(DATE_FIELD).asText();

        Question newQuestion = new Question();
        newQuestion.setType(questionType);
        newQuestion.setQuestion(question);
        newQuestion.setTopic(topic);
        newQuestion.setTags(tags);
        newQuestion.setCompetency(competency);
        newQuestion.setA(a);
        newQuestion.setB(b);
        newQuestion.setC(c);
        newQuestion.setD(d);
        newQuestion.setAnswer(answer);
        newQuestion.setSeen(seenOnInterview);
        newQuestion.setDate(date);

        return newQuestion;
    }
}
