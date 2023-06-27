package com.sisp.dao;

import com.sisp.entity.QuestionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionEntityMapper {
    int insert(QuestionEntity  questionEntity);

    List<QuestionEntity> queryQuestionList(QuestionEntity questionEntity);

    List<QuestionEntity> queryTemplateQuestionList(QuestionEntity questionEntity);


}
