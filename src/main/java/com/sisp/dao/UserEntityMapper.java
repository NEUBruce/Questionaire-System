package com.sisp.dao;

import com.sisp.dao.entity.UserEntity;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


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

    List<UserEntity> selectUserInfo(UserEntity userEntity);

    int deleteUserByName(UserEntity userEntity);




}
