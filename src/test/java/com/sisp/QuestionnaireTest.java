package com.sisp;

import com.sisp.controller.ProjectController;
import com.sisp.controller.QuestionnaireController;
import com.sisp.entity.QuestionnaireEntity;
import jakarta.annotation.Resource;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionnaireTest {
    @Resource
    private QuestionnaireController questionnaireController;
    Logger log = Logger.getLogger(ProjectsTests.class);

    @Test
    public void testQueryQuestionnaire() {
        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        questionnaireEntity.setProjectId("c1ec9918a02849eab6af057feb8b7b80");
        System.out.println(questionnaireController.queryQuestionnaireList(questionnaireEntity).getData());

    }
}
