package com.sisp;

import com.sisp.controller.QuestionnaireController;
import com.sisp.entity.QuestionnaireEntity;
import jakarta.annotation.Resource;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionnaireTests {
    @Resource
    private QuestionnaireController questionnaireController;
    Logger log = Logger.getLogger(ProjectsTests.class);

    @Test
    public void testQueryQuestionnaireSucceed() {
        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        questionnaireEntity.setProjectId("c1ec9918a02849eab6af057feb8b7b80");
        System.out.println(questionnaireController.queryQuestionnaireList(questionnaireEntity).getData());

    }

    @Test
    public void testQueryQuestionnaireFail() {
        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        questionnaireEntity.setProjectId("hhhhhhhh");
        System.out.println(questionnaireController.queryQuestionnaireList(questionnaireEntity).getData());
    }

    @Test
    public void testUpdateQuestionnaireSucceed() {
        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        questionnaireEntity.setId("addb14ce3dad4d34a37ad58c287c59d8");
        questionnaireEntity.setQuestionnaireName("修改成功");
        System.out.println(questionnaireController.modifyQuestionnaire(questionnaireEntity));
    }

    @Test
    public void testUpdateQuestionnaireFail() {
        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        questionnaireEntity.setId("11111112233");
        questionnaireEntity.setQuestionnaireName("修改失败");
        System.out.println(questionnaireController.modifyQuestionnaire(questionnaireEntity));
    }

    @Test
    public void testAddQuestionnaire() {
        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        questionnaireEntity.setProjectId("c1ec9918a02849eab6af057feb8b7b80");
        questionnaireEntity.setQuestionnaireName("添加成功");
        System.out.println(questionnaireController.addQuestionnaire(questionnaireEntity));
    }

    @Test
    public void testDeleteQuestionnaireSucceed() {
        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        questionnaireEntity.setId("addb14ce3dad4d34a37ad58c287c59d8");
        questionnaireEntity.setQuestionnaireName("删除成功");
        System.out.println(questionnaireController.deleteQuestionnaire(questionnaireEntity));
    }

    @Test
    public void testDeleteQuestionnaireFailed() {
        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        questionnaireEntity.setId("a1111");
        questionnaireEntity.setQuestionnaireName("删除失败");
        System.out.println(questionnaireController.deleteQuestionnaire(questionnaireEntity));
    }

}
