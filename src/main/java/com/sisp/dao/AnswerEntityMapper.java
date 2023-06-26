package com.sisp.dao;

import com.sisp.entity.AnswerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AnswerEntityMapper {
    List<AnswerEntity> queryAnswerEntityList(AnswerEntity answerEntity);
}
