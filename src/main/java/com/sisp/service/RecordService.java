package com.sisp.service;

import com.sisp.common.utils.UUIDUtil;
import com.sisp.controller.RecordController;
import com.sisp.dao.AnswerEntityMapper;
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
    private AnswerEntityMapper answerEntityMapper;

    /**
     * 查询答题记录列表
     * @param recordEntity
     * @return
     */
    public List<RecordEntity> queryRecordEntityList(RecordEntity recordEntity) {

        List<RecordEntity> recordEntityList = recordEntityMapper.queryRecordList(recordEntity);
        if (recordEntityList != null) {
            for (RecordEntity item :recordEntityList) {
                AnswerEntity answerEntity = new AnswerEntity();
                answerEntity.setRecordId(item.getId());
                item.setAnswerEntityList(answerEntityMapper.queryAnswerList(answerEntity));
            }
        }


        return recordEntityList;
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
            answerEntity.setId(UUIDUtil.getOneUUID());
            int tmp = answerEntityMapper.insert(answerEntity);
            res = tmp > 0 ? res : tmp;
        }

        return res;
    }
}
