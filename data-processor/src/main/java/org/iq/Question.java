package org.iq;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;
import org.iq.enums.Competency;
import org.iq.enums.QuestionType;
import org.iq.utils.QuestionDeserializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Your csv should have at least following headers:
** question - represents the actual question being asked;
** questionType - identifies question type as either simple TEXT, CODE, SINGLE answer (radio), or MULTI (checkbox);
** topic - high-level category for the question, often reflecting a knowledge area (e.g., Databases, Testing Theory, etc.);
** tags - list up to three tags for precise question identification. Each tag should be in a separate column;
** competency - the level of expertise or knowledge needed to respond to the question: Junior, Middle or Senior;
** a,b,c,d - possible answer options for questions with types SINGLE or MULTI. \
    A minimum of two options is necessary for functionality, though including all four is recommended for an enhanced user experience;
** answer - contains plain text for TEXT questions or letters a); b); c); d) (separated by semicolons) for SINGLE or MULTI;
** date - the date when question was added or last modified;
** seenOnInterview - whether the particular question was asked on interview;
** disclaimer - contains some useful for end-user information based on specific condition.
*/

@Log
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
// @JsonDeserialize(using = QuestionDeserializer.class) TODO: postponed for the next release
public class Question {
    private QuestionType type = QuestionType.TEXT;
    private String question;
    private String topic;
    private List<String> tags = new ArrayList<>();
    private Competency competency = Competency.Junior;
    private String a;
    private String b;
    private String c;
    private String d;
    private String answer;
    private LocalDate date;
    private boolean seen = false;
    private String disclaimer;

    @JsonIgnore
    private static final Map<String, String> replacementList = new HashMap<>() {{
        put("[*", "<span class='bold'>"); // Bold text: Begin
        put("*]", "</span>"); // Bold text: End
        put("/*", "<span class='italic'>"); // Italic text: Begin
        put("*/", "</span>"); // Italic text: End
        put("//", "<br>"); // Italic text: End
    }};

    public void setType(String type) {
        this.type = QuestionType.fromString(type);
    }

    public void setCompetency(String competency) {
        this.competency = Competency.fromString(competency);
    }

    public void setA(String a) {
        if (a != null) {
            this.a = "a) " + setHtmlMarkup(a);
        }
    }

    public void setB(String b) {
        if (b != null) {
            this.b = "b) " + setHtmlMarkup(b);
        }
    }

    public void setC(String c) {
        if (c != null) {
            this.c = "c) " + setHtmlMarkup(c);
        }
    }

    public void setD(String d) {
        if (d != null) {
            this.d = "d) " + setHtmlMarkup(d);
        }
    }

    public void setTags(String tag) {
        if (tag != null) {
            tags.add(tag);
        }
    }

    public void setTags(List<String> tags) {
        this.tags.addAll(tags);
    }

    public void setQuestion(String question) {
        this.question = setHtmlMarkup(question);
    }

    public void setAnswer(String answer) {
        if (answer != null) {
            this.answer = setHtmlMarkup(answer);
        }
    }

    public void setDate(String date) {
        if (date != null && !date.isEmpty()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.date = LocalDate.parse(date, dtf);
        }
    }

    public void setSeen(String seen) {
        if (seen.equalsIgnoreCase("True")) {
            this.seen = true;
        }
    }

    public void setDisclaimer() {
        if (QuestionType.CODE.equals(this.type)) {
            this.disclaimer = "Please note that this is a CODE question, and a direct answer is not provided. " +
                    "This is because there are often multiple solutions to a coding problem, and the focus " +
                    "is on providing guidance and advice rather than a single correct answer.";
        } else if (this.topic.equalsIgnoreCase("Behavioral")) {
            this.disclaimer = "Please note that this is a Behavioral question, and a direct answer is not provided. " +
                    "This is because responses may vary based on individual experiences and perspectives and the focus " +
                    "is on providing guidance and advice rather than a predetermined response.";
        }
    }

    private String setHtmlMarkup(String text) {
        String result = text;

        if (result != null) {
            for (String tag : replacementList.keySet()) {
                result = result.replace(tag, replacementList.get(tag));
            }
        }

        return result;
    }

    public boolean isNew() {
        if (this.date != null) {
            LocalDate currentDate = LocalDate.now();
            LocalDate questionDate = this.date;

            long daysBetween = ChronoUnit.DAYS.between(questionDate, currentDate);

            return daysBetween <= 30;
        }
        return false;
    }

    public boolean isValid() {
        if (this.question == null | this.answer == null | this.topic == null) {
            return false;
        } else if (QuestionType.SINGLE.equals(this.type) | QuestionType.MULTI.equals(this.type)) {
            return !(a == null);
        }
        return true;
    }
}
