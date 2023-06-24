package com.sisp.service;

import com.sisp.common.utils.UUIDUtil;
import com.sisp.dao.ProjectEntityMapper;
import com.sisp.entity.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 项目业务逻辑层
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectEntityMapper projectEntityMapper;

    /**
     * 查询项目列表
     * @param projectEntity
     * @return
     */
    public List<ProjectEntity> queryProjectList(ProjectEntity projectEntity) {
        return projectEntityMapper.queryProjectList(projectEntity);
    }


    /**
     * 添加项目信息
     * @param projectEntity
     * @return
     */
    public int addProjectInfo(ProjectEntity projectEntity) {
        projectEntity.setId(UUIDUtil.getOneUUID());
        projectEntity.setCreationDate(new Date());
        projectEntity.setLastUpdateDate(new Date());
        int result = projectEntityMapper.insert(projectEntity);
        if (result == 0) {
            return 0;
        } else {
            return 3;
        }
    }

    /**
     * 根据主键修改项目信息
     * @param projectEntity
     * @return
     */
    public int modifyProjectInfo(ProjectEntity projectEntity) {
        projectEntity.setLastUpdateDate(new Date());
        int result = projectEntityMapper.updateByPrimaryKeySelective(projectEntity);
        if (result == 0) {
            return 0;
        }else {
            return 3;
        }
    }

    /**
     * 根据主键删除项目信息
     * @param projectEntity
     * @return
     */
    public int deleteProjectById(ProjectEntity projectEntity) {
        int result = projectEntityMapper.deleteProjectById(projectEntity);
        if (result == 0) {
            return 0;
        }else {
            return 3;
        }
    }
}
