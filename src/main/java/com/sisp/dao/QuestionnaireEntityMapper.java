package com.sisp.dao;

import com.sisp.entity.QuestionnaireEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionnaireEntityMapper {
    int insert(QuestionnaireEntity questionnaireEntity);
}
