package com.sisp.dao;

import com.sisp.entity.ProjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目实体mapper
 */
@Repository
@Mapper
public interface ProjectEntityMapper {
    /**
     * 修改项目
     * @param projectEntity
     * @return
     */
    int updateByPrimaryKeySelective(ProjectEntity projectEntity);

    /**
     * 根据主键删除项目
     * @param projectEntity
     * @return
     */
    int deleteProjectById(ProjectEntity projectEntity);

    /**
     * 查询所有项目
     * @param projectEntity
     * @return
     */
    List<ProjectEntity> queryProjectList(ProjectEntity projectEntity);

    /**
     * 添加项目
     * @param projectEntity
     * @return
     */
    int insert(ProjectEntity projectEntity);

}
