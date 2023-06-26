package com.sisp.service;

import com.sisp.controller.RecordController;
import com.sisp.dao.RecordEntityMapper;
import com.sisp.entity.RecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {
    @Autowired
    private RecordEntityMapper recordEntityMapper;

    public List<RecordEntity> queryRecordEntityList(RecordEntity recordEntity) {
        return recordEntityMapper.queryRecordList(recordEntity);
    }

    public int insert(RecordEntity recordEntity) {
        return recordEntityMapper.insert(recordEntity);
    }
}
