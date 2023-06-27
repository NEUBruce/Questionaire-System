package com.sisp.dao;

import com.sisp.entity.QuestionEntity;
import com.sisp.entity.QuestionnaireEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionEntityMapper {
    int insert(QuestionEntity  questionEntity);

    List<QuestionEntity> queryQuestionList(QuestionEntity questionEntity);

    List<QuestionEntity> queryTemplateQUestionList(QuestionEntity questionEntity);


}
