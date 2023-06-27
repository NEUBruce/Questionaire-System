package com.sisp.service;

import com.sisp.common.utils.UUIDUtil;
import com.sisp.controller.RecordController;
import com.sisp.dao.RecordEntityMapper;
import com.sisp.entity.AnswerEntity;
import com.sisp.entity.RecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 答题记录的业务逻辑层
 */
@Service
public class RecordService {
    @Autowired
    private RecordEntityMapper recordEntityMapper;
    @Autowired
    private AnswerService answerService;

    /**
     * 查询答题记录列表
     * @param recordEntity
     * @return
     */
    public List<RecordEntity> queryRecordEntityList(RecordEntity recordEntity) {
        return recordEntityMapper.queryRecordList(recordEntity);
    }

    /**
     * 添加答题记录
     * @param recordEntity
     * @return
     */
    public int insert(RecordEntity recordEntity) {

        recordEntity.setId(UUIDUtil.getOneUUID());
        recordEntity.setAnswerDate(new Date());
        int res = recordEntityMapper.insert(recordEntity);
        for (AnswerEntity answerEntity : recordEntity.getAnswerEntityList()) {
            answerEntity.setRecordId(recordEntity.getId());
            int tmp = answerService.insert(answerEntity);
            res = tmp > 0 ? res : tmp;
        }

        return res;
    }
}
