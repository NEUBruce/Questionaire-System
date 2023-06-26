package com.sisp.dao;

import com.sisp.entity.RecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RecordEntityMapper {

    List<RecordEntity> queryRecordList(RecordEntity recordEntity);

    int insert(RecordEntity recordEntity);
}
