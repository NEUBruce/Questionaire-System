package com.sisp.dao;

import com.sisp.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 用户实体mapper
 */
@Repository
@Mapper
public interface UserEntityMapper {
    /**
     * 查询数据库用户列表
     * @return
     */
    List<UserEntity> queryUserList(UserEntity userEntity);


    /**
     * 创建用户
     * @param userEntity
     * @return
     */
    int insert(UserEntity userEntity);

    /**
     * 根据id删除用户信息
     * @param userEntity
     * @return
     */
    int deleteUserById(UserEntity userEntity);


    /**
     * 根据id修改用户信息
     * @param userEntity
     * @return
     */
    int updateByPrimaryKeySelective(UserEntity userEntity);

    /**
     * 根据用户账号与密码查询用户
     * @param userEntity
     * @return
     */
    List<UserEntity> selectUserInfo(UserEntity userEntity);

    /**
     * 根据名字删除用户
     * @param userEntity
     * @return
     */
    int deleteUserByName(UserEntity userEntity);




}
