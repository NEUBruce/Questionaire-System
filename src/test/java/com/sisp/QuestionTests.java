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

import java.util.List;

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

    @Test
    public void testQueryTemplateQuestion() {
        QuestionEntity questionEntity = new QuestionEntity();
        List<QuestionEntity> questionEntityList = (List<QuestionEntity>) questionController.queryTemplateQuestionList(questionEntity).getData();

        System.out.println(questionEntityList);
    }

    @Test
    public void testQueryQuestionListSuccees() {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId("f8bddfb4804342319206c8fb36fece53");
        List<QuestionEntity> questionEntityList = (List<QuestionEntity>) questionController.queryQuestionList(questionEntity).getData();

        System.out.println(questionEntityList);
    }

    @Test
    public void testQueryQuestionListFail() {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId("1111");
        List<QuestionEntity> questionEntityList = (List<QuestionEntity>) questionController.queryQuestionList(questionEntity).getData();

        System.out.println(questionEntityList);
    }


}
