package com.sisp.dao;

import com.sisp.controller.QuestionnaireController;
import com.sisp.entity.QuestionnaireEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionnaireEntityMapper {
    int insert(QuestionnaireEntity questionnaireEntity);

    List<QuestionnaireEntity> queryQuestionnaireList(QuestionnaireEntity questionnaireEntity);

    int updateByPrimaryKeySelective(QuestionnaireEntity questionnaireEntity);

    int deleteQuestionnaire(QuestionnaireEntity questionnaireEntity);
}
