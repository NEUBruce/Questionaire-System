package com.sisp;


import com.sisp.controller.QuestionController;
import com.sisp.controller.QuestionnaireController;
import com.sisp.entity.QuestionEntity;
import jakarta.annotation.Resource;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionTests {
    @Resource
    private QuestionController questionController;
    Logger log = Logger.getLogger(ProjectsTests.class);

    @Test
    public void testAddQuestion() {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setQuestionnaireId("64b4747a02074a22b135e2ba70bfc102");
        questionController.addQuestion(questionEntity);
    }



}
