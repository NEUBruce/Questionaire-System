package com.sisp.dao;

import com.sisp.entity.QuestionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface QuestionEntityMapper {
    int insert(QuestionEntity  questionEntity);


}
