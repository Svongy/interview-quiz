package org.iq;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.iq.enums.QuestionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Your csv should have at least following headers:
** question - actual question data;
** questionType - the type of question, which can be: TEXT, CODE, SINGLE, MULTI;
** topic - high-level topic for the question, usually represents technology (Databases, Testing theory etc.);
** tags - list of up to 3 tags, required for precise question identification. Each tag is a separate column;
** competency - level of competency required to answer the question. Gradation of Junior, Middle and Senior;
** a,b,c,d - possible answer options;
** answer - the answer to a question, contain a); b); c); d), N/A for coding questions or with text.
*/

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Question {
    private QuestionType type;
    private String question;
    private String topic = "N/A - ERROR IN DATASET";
    private List<String> tags = new ArrayList<>();
    private String competency = "N/A - ERROR IN DATASET";
    // private List<String> options = new ArrayList<>();
    private String a;
    private String b;
    private String c;
    private String d;
    private String answer;

    @JsonIgnore
    private static final Map<String, String> replacementList = new HashMap<>() {{
        put("[*", "<span class='bold'>"); // Bold text: Begin
        put("*]", "</span>"); // Bold text: End
        put("/*", "<span class='italic'>"); // Italic text: Begin
        put("*/", "</span>"); // Italic text: End
        put("//", "<br>"); // Italic text: End
    }};

    public void setType(String type) {
        this.type = QuestionType.valueOf(type);
    }

    public void setA(String a) {
        if (a != null) {
            this.a = "a) " + a;
        }
    }

    public void setB(String b) {
        if (b != null) {
            this.b = "b) " + b;
        }
    }

    public void setC(String c) {
        if (c != null) {
            this.c = "c) " + c;
        }
    }

    public void setD(String d) {
        if (d != null) {
            this.d = "d) " + d;
        }
    }

    //    @JsonProperty("options")
//    @JsonAlias({"a", "b", "c", "d"})
//    public void setOptions(String option) {
//        if (option != null) {
//            options.add(option);
//        }
//    }



    public void setTags(String tag) {
        if (tag != null) {
            tags.add(tag);
        }
    }

    public void setQuestion(String question) {
        this.question = setHtmlMarkup(question);
    }

    public void setAnswer(String answer) {
        if (answer != null) {
            this.answer = setHtmlMarkup(answer);
        }
    }


    private String setHtmlMarkup(String text) {
        String result = text;

        for (String tag : replacementList.keySet()) {
            result = result.replace(tag, replacementList.get(tag));
        }

        return result;
    }
}
