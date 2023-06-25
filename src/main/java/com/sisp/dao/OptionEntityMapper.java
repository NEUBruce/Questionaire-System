package com.sisp.dao;

import com.sisp.entity.OptionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OptionEntityMapper {
    int insert(OptionEntity optionEntity);
}
