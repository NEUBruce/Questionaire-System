package com.sisp.service;

import com.sisp.common.utils.UUIDUtil;
import com.sisp.dao.UserEntityMapper;
import com.sisp.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户业务逻辑层
 */
@Service
public class UserService {
    @Autowired
    private UserEntityMapper userEntityMapper;

    /**
     * 查询用户
     * @param userEntity
     * @return
     */
    public List<UserEntity> selectUserInfo(UserEntity userEntity) {
        return userEntityMapper.selectUserInfo(userEntity);
    }

    /**
     * 查询用户
     * @return
     */
    public List<UserEntity> queryUserList(UserEntity userEntity) {
        return userEntityMapper.queryUserList(userEntity);
    }

    /**
     * 添加用户
     * @param userEntity
     * @return
     */
    public int addUserInfo(UserEntity userEntity) {
        userEntity.setId(UUIDUtil.getOneUUID());
        userEntity.setStatus("1");
        userEntity.setCreationDate(new Date());
        userEntity.setLastUpdateDate(new Date());
        int userResult = userEntityMapper.insert(userEntity);
        if (userResult != 0) {
            return 3;
        }else {
            return userResult;
        }
    }

    /**
     * 修改用户信息
     * @param userEntity
     * @return
     */
    public int modifyUserInfo(UserEntity userEntity) {
        userEntity.setLastUpdateDate(new Date());
        int userResult = userEntityMapper.updateByPrimaryKeySelective(userEntity);

        return userResult;
    }

    /**
     * 删除用户信息
     * @param userEntity
     * @return
     */
    public int deleteUserById(UserEntity userEntity) {
        int userResult = userEntityMapper.deleteUserById(userEntity);

        return userResult;
    }


}
