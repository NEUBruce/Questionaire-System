package com.sisp.controller;

import com.sisp.beans.HttpResponseEntity;
import com.sisp.entity.UserEntity;
import com.sisp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户controller
 */
@RestController
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity login(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            List<UserEntity> hasUser = userService.selectUserInfo(userEntity);
            if (hasUser.isEmpty()) {
                httpResponse.setCode("0");
                httpResponse.setData(hasUser);
                httpResponse.setMessage("登录失败!");
            }else {

                httpResponse.setCode("666");
                httpResponse.setData(hasUser.get(0));
                httpResponse.setMessage("登录成功!");
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }

        return httpResponse;

    }

    /**
     * 查询用户列表
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/queryUserList", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryUserList(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            List<UserEntity> users = userService.queryUserList(userEntity);
            System.out.println(users);
            if (users.isEmpty()) {
                httpResponse.setCode("0");
                httpResponse.setData(users);
                httpResponse.setMessage("没有用户!");
            }else {
                httpResponse.setCode("666");
                httpResponse.setData(users);
                httpResponse.setMessage("查询成功!");
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }

        return httpResponse;

    }


    /**
     * 添加用户
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addUser(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            int result = userService.addUserInfo(userEntity);
            if (result == 0) {
                httpResponse.setCode("0");
                httpResponse.setData(0);
                httpResponse.setMessage("创建失败!");
            }else {
                httpResponse.setCode("666");
                httpResponse.setData(result);
                httpResponse.setMessage("创建成功!");
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }

        return httpResponse;

    }

    /**
     * 删除用户
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/deleteUserinfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteUser(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            int result = userService.deleteUserById(userEntity);
            if (result == 0) {
                httpResponse.setCode("0");
                httpResponse.setData(0);
                httpResponse.setMessage("删除失败!");
            }else {
                httpResponse.setCode("666");
                httpResponse.setData(result);
                httpResponse.setMessage("删除成功!");
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }

        return httpResponse;

    }

    /**
     * 修改用户
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/modifyUserInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyUser(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            int result = userService.modifyUserInfo(userEntity);
            System.out.println(result);
            if (result == 0) {
                httpResponse.setCode("0");
                httpResponse.setData(0);
                httpResponse.setMessage("修改失败!");
            }else {
                httpResponse.setCode("666");
                httpResponse.setData(result);
                httpResponse.setMessage("修改成功!");
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }

        return httpResponse;

    }



}
