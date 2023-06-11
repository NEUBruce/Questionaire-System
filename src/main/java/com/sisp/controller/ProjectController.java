package com.sisp.controller;

import com.sisp.beans.HttpResponseEntity;
import com.sisp.entity.ProjectEntity;
import com.sisp.entity.UserEntity;
import com.sisp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 项目controller
 */
@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    /**
     * 根据项目名称或者所有者名称查询项目
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/queryProjectList", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryProjectList(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            List<ProjectEntity> projects = projectService.queryProjectList(projectEntity);
            System.out.println(projects);
            if (projects.isEmpty()) {
                httpResponse.setCode("0");
                httpResponse.setData(projects);
                httpResponse.setMessage("没有项目!");
            }else {
                httpResponse.setCode("666");
                httpResponse.setData(projects);
                httpResponse.setMessage("查询成功!");
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }

        return httpResponse;

    }


    /**
     * 添加新项目
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/addProjectInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addProjectInfo(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            int result = projectService.addProjectInfo(projectEntity);
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
     * 修改项目信息
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/modifyProjectInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyProjectInfo(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            int result = projectService.modifyProjectInfo(projectEntity);
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

    /**
     * 根据项目id删除项目
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/deleteProjectById", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteProjectById(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            int result = projectService.deleteProjectById(projectEntity);
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
}
