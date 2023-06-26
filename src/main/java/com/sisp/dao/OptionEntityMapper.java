package com.sisp.dao;

import com.sisp.entity.OptionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OptionEntityMapper {
    int insert(OptionEntity optionEntity);

    List<OptionEntity> queryOptionList(OptionEntity optionEntity);
}
