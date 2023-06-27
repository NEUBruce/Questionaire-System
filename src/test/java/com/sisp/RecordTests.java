package com.sisp;

import com.sisp.controller.QuestionController;
import com.sisp.controller.RecordController;
import com.sisp.entity.RecordEntity;
import jakarta.annotation.Resource;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecordTests {

    @Resource
    private RecordController recordController;
    Logger log = Logger.getLogger(ProjectsTests.class);

    @Test
    public void testAddRecord(){
        RecordEntity recordEntity = new RecordEntity();
        recordEntity.setAnsweredBy("aaaaa");
        recordController.addRecord(recordEntity);

    }

    @Test
    public void testQueryRecordSuccess(){
        RecordEntity recordEntity = new RecordEntity();
        recordEntity.setId("848c3bfcf73342c8b8815aa353a3887e");
        List<RecordEntity> recordEntityList = (List<RecordEntity>) recordController.queryQuestionList(recordEntity).getData();

        System.out.println(recordEntityList);

    }

    @Test
    public void testQueryRecordFail(){
        RecordEntity recordEntity = new RecordEntity();
        recordEntity.setId("1112222");
        List<RecordEntity> recordEntityList = (List<RecordEntity>) recordController.queryQuestionList(recordEntity).getData();

        System.out.println(recordEntityList);

    }
}
