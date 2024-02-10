package org.iq.service;

import org.iq.quiz.QuizModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {
    QuizModel quizModel = new QuizModel();

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("dataLoaded", quizModel.isDataLoaded());
        return "index";
    }

    @PostMapping("/loadQuestions")
    public String loadData() {
        quizModel.loadData();
        quizModel.generateQuestionsList();
        return "redirect:build";
    }

    @GetMapping("/build")
    public String build(Model model) {
        if (!quizModel.isDataLoaded()) {
            return "index";
        } else {
            model.addAttribute("topics", quizModel.getDistinctTopics());
            model.addAttribute("tags", quizModel.getDistinctTags());
            model.addAttribute("competencies", quizModel.getDistinctCompetencies());
            return "build";
        }
    }

    @GetMapping("/generateQuiz")
    public String generateQuiz(@RequestParam(value = "topics", required = false) List<String> topics,
                               @RequestParam(value = "tags", required = false) List<String> tags,
                               @RequestParam(value = "competencies", required = false) List<String> competencies) {
        quizModel.generateQuiz(topics, tags, competencies);
        return "redirect:quiz";
    }

    @GetMapping("/quiz")
    public String quizPage(Model model) {
        if (!quizModel.isDataLoaded()) {
            return "index";
        }

        if (quizModel.getReadyQuestions().isEmpty()) {
            model.addAttribute("errorHeader", "No questions found");
            model.addAttribute("errorMessage",
                    "To adjust your selection click \"Try again\" and consider changing Topics, Tags, or Competencies");
            return "error";
        } else {
            model.addAttribute("questions", quizModel.getReadyQuestions());
            model.addAttribute("limits", quizModel.getQuestionsLimit());
            return "quiz";
        }
    }
}
