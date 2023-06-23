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
class UserTests {

    @Resource
    private UserController userController;
    @Resource
    private ProjectController projectController;
    Logger log = Logger.getLogger(UserTests.class);

    @Test
    public void deleteUserByName() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserEntityMapper userEntityMapper = sqlSession.getMapper(UserEntityMapper.class);
        //调用userMapper的方法
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("aaaaa");
        int i = userEntityMapper.deleteUserByName(userEntity);
        if(i==0){
            // 记录error级别的信息
        }else{
            System.out.println(i);
            // 记录info级别的信息
            log.info(">>delete用户删除测试成功");
        }
        sqlSession.close();
    }
    @Test
    public void queryUserList() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserEntityMapper userEntityMapper = sqlSession.getMapper(UserEntityMapper.class);
        //调用userMapper的方法
        UserEntity userEntity = new UserEntity();
        List<UserEntity> list = userEntityMapper.queryUserList(userEntity);
        if(CollectionUtils.isEmpty(list)){
            // 记录error级别的信息
        }else{
            System.out.println(list);
            // 记录info级别的信息
            log.info(">>queryUserList用户列表查询测试成功");
        }
        sqlSession.close();
    }
    @Test
    public void selectUserInfo() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserEntityMapper userEntityMapper = sqlSession.getMapper(UserEntityMapper.class);
        //调用userMapper的方法
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setPassword("123");
        List<UserEntity> list = userEntityMapper.selectUserInfo(userEntity);
        if(CollectionUtils.isEmpty(list)){
            // 记录error级别的信息
        }else{
            System.out.println(list);
            // 记录info级别的信息
            log.info(">>qselectUserInfo用户登录测试成功");
        }
        sqlSession.close();
    }

    @Test
    public void insert() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserEntityMapper userEntityMapper = sqlSession.getMapper(UserEntityMapper.class);
        //调用userMapper的方法
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUIDUtil.getOneUUID());
        userEntity.setStatus("1");
        userEntity.setUsername("LS");
        userEntity.setPassword("123");
        int i = userEntityMapper.insert(userEntity);
        if(i==0){
            // 记录error级别的信息
        }else{
            System.out.println(i);
            // 记录info级别的信息
            log.info(">>insert用户插入测试成功");
        }

        sqlSession.close();
    }

    @Test
    public void testSelectUserFound() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setId("8a175b35b1ca4d2ab09b344d5d4b5461");
        userEntity.setCreatedBy("hh");
        userEntity.setLastUpdatedBy("hh");
        userEntity.setLastUpdateDate(new Date());
        userEntity.setCreationDate(new Date());
        HttpResponseEntity httpResponse = userController.queryUserList(userEntity);

        log.info("========结果========");
    }

    @Test
    public void testSelectUserNotFound() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("hhh");
        HttpResponseEntity httpResponse = userController.queryUserList(userEntity);

        log.info("========结果========");
    }

    @Test
    public void testAddUser() {
        UserEntity userEntity = new UserEntity();
        userController.addUser(userEntity);

        log.info("========结果========");

    }


   @Test
    public void testDeleteUserNotFound() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId("3");
        userController.deleteUser(userEntity);

    }
    @Test
    public void testDeleteUserFound() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId("a8def0d7e2494e37a191cf471c2df9b5");
        userController.deleteUser(userEntity).getData();
        log.info("========结果========");

    }

    @Test
    public void testLoginPass() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setPassword("123");
        userEntity.setStatus("1");
        List<UserEntity> result =  (List<UserEntity>) userController.login(userEntity).getData();

        log.info("========结果========");
        log.info(result);

    }

    @Test
    public void testLoginFail() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("11111");
        userEntity.setPassword("1jjj");
        Object result =  userController.login(userEntity).getData();

        log.info("========结果========");
        log.info(result);

    }

    @Test
    public void testModifyUserSuccess() {

        UserEntity userEntity = new UserEntity();
        userEntity.setId("8a175b35b1ca4d2ab09b344d5d4b5461");
        userEntity.setUsername("11111");
        userEntity.setPassword("1jjj");
        int res = (int) userController.modifyUser(userEntity).getData();

        log.info("========结果========");
        log.info(res);

    }

    @Test
    public void testModifyUserFail() {

        UserEntity userEntity = new UserEntity();
        userEntity.setId("hhhhh");
        userEntity.setUsername("11111");
        userEntity.setPassword("1jjj");
        userController.modifyUser(userEntity).getData();

        log.info("========结果========");

    }
}
