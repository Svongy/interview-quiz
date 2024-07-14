package org.iq.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebControllerTest {

    @Autowired
    private WebController webController;

    @Test
    void indexPage_defaults(){
        Model model = mock(Model.class);
        String result = webController.indexPage(model);
        assertFalse(webController.quizModel.isDataLoaded());
        assertNull(webController.quizModel.getRawQuestions());
        assertNull(webController.quizModel.getReadyQuestions());
        assertEquals(6, webController.quizModel.getQuestionsLimit().length);
        assertEquals("index", result);
    }

    @Test
    void loadQuestions_valid(){
        Model model = mock(Model.class);
        String resultLoadData = webController.loadData();
        String resultQuiz = webController.generateQuiz(null, null, null);
        assertTrue(webController.quizModel.isDataLoaded());
        assertNotNull(webController.quizModel.getRawQuestions());
        assertNotNull(webController.quizModel.getReadyQuestions());
        assertEquals("redirect:build", resultLoadData);
        assertEquals("redirect:quiz", resultQuiz);
    }

    @Test
    void buildQuiz_valid(){
        Model model = mock(Model.class);
        webController.loadData();
        webController.generateQuiz(null, null, null);
        String result = webController.build(model);
        assertEquals("build", result);
    }

    @Test
    void generateQuiz_valid(){
        Model model = mock(Model.class);
        webController.loadData();
        webController.generateQuiz(null, null, null);
        String result = webController.quizPage(model);
        assertEquals("quiz", result);
    }

    @Test
    void buildQuiz_noData_invalid(){
        WebController webControllerInternal = new WebController();
        Model model = mock(Model.class);
        String result = webControllerInternal.build(model);
        assertEquals("index", result);
    }

    @Test
    void generateQuiz_noData_invalid(){
        WebController webControllerInternal = new WebController();
        Model model = mock(Model.class);
        String result = webControllerInternal.quizPage(model);
        assertEquals("index", result);
    }
}
