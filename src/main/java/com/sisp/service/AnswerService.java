package com.sisp.service;

import com.sisp.common.utils.UUIDUtil;
import com.sisp.dao.AnswerEntityMapper;
import com.sisp.entity.AnswerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 选项记录的业务逻辑
 */
@Service
public class AnswerService {
    @Autowired
    private AnswerEntityMapper answerEntityMapper;

    /**
     * 查询选项记录列表
     * @param answerEntity
     * @return
     */
    public List<AnswerEntity> queryAnswerList(AnswerEntity answerEntity) {
        return answerEntityMapper.queryAnswerEntityList(answerEntity);
    }

    /**
     * 插入选项记录
     * @param answerEntity
     * @return
     */
    public int insert(AnswerEntity answerEntity) {
        answerEntity.setId(UUIDUtil.getOneUUID());
        return answerEntityMapper.insert(answerEntity);
    }
}
