package com.sisp.service;

import com.sisp.controller.RecordController;
import com.sisp.dao.RecordEntityMapper;
import com.sisp.entity.RecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 答题记录的业务逻辑层
 */
@Service
public class RecordService {
    @Autowired
    private RecordEntityMapper recordEntityMapper;

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
        return recordEntityMapper.insert(recordEntity);
    }
}
