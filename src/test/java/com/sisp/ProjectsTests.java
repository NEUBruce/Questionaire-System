package com.sisp;

import com.sisp.beans.HttpResponseEntity;
import com.sisp.common.utils.UUIDUtil;
import com.sisp.controller.ProjectController;
import com.sisp.controller.UserController;
import com.sisp.dao.UserEntityMapper;
import com.sisp.entity.ProjectEntity;
import com.sisp.entity.UserEntity;
import jakarta.annotation.Resource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProjectsTests {
//    @Test
//    void contextLoads() {

    //    }

    @Resource
    private UserController userController;
    @Resource
    private ProjectController projectController;
    Logger log = Logger.getLogger(ProjectsTests.class);

    @Test
    public void testSelectProjectSuccess() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectName("清华大学");
        projectController.queryProjectList(projectEntity).getData();

        log.info("========结果========");
    }

    @Test
    public void testSelectProjectFail() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectName("中砂湖南大学");
        projectController.queryProjectList(projectEntity).getData();

        log.info("========结果========");
    }

    @Test
    public void testAddProject() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setUserId("1");
        projectEntity.setCreationDate(new Date());
        projectEntity.setLastUpdateDate(new Date());
        System.out.println(projectEntity);
        projectController.addProjectInfo(projectEntity).getData();

        log.info("========结果========");
    }

    @Test
    public void testDeleteProjectFail() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId("999");
        projectController.deleteProjectById(projectEntity).getData();

        log.info("========结果========");
    }

    @Test
    public void testDeleteProjectSuccess() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId("2");
        projectController.deleteProjectById(projectEntity).getData();

        log.info("========结果========");
    }

    @Test
    public void testUpdateProjectSuccess() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId("2");
        projectEntity.setProjectName("hh");
        projectController.modifyProjectInfo(projectEntity).getData();

        log.info("========结果========");
    }

    @Test
    public void testUpdateProjectFail() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId("00000");
        projectEntity.setProjectName("hh");
        projectController.modifyProjectInfo(projectEntity).getData();

        log.info("========结果========");

    }




}
