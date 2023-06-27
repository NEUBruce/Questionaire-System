package com.sisp.dao;

import com.sisp.entity.AnswerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 选择记录的Mapper
 */
@Repository
@Mapper
public interface AnswerEntityMapper {
    /**
     * 查询选择记录
     * @param answerEntity
     * @return
     */
    List<AnswerEntity> queryAnswerList(AnswerEntity answerEntity);

    /**
     * 插入选择记录
     * @param answerEntity
     * @return
     */
    int insert(AnswerEntity answerEntity);
}
