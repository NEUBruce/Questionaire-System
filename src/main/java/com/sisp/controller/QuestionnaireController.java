package com.sisp.controller;

import com.sisp.beans.HttpResponseEntity;
import com.sisp.entity.QuestionnaireEntity;
import com.sisp.entity.UserEntity;
import com.sisp.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 问卷controller
 */
@RestController
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;

    /**
     * 添加问卷
     * @param questionnaireEntity
     * @return
     */
    @RequestMapping(value = "/addQuestionnaire", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addQuestionnaire(@RequestBody QuestionnaireEntity questionnaireEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            QuestionnaireEntity result = questionnaireService.addQuestionnaire(questionnaireEntity);
            if (result == null) {
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
     * 查询问卷列表
     * @param questionnaireEntity
     * @return
     */
    @RequestMapping(value = "/queryQuestionnaireList", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryQuestionnaireList(@RequestBody QuestionnaireEntity questionnaireEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            List<QuestionnaireEntity> questionnaireEntityList = questionnaireService.queryQuestionnaireList(questionnaireEntity);
            if (questionnaireEntityList.isEmpty()) {
                httpResponse.setCode("0");
                httpResponse.setData(questionnaireEntityList);
                httpResponse.setMessage("没有问卷!");
            }else {
                httpResponse.setCode("666");
                httpResponse.setData(questionnaireEntityList);
                httpResponse.setMessage("查询成功!");
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }

        return httpResponse;

    }

    /**
     * 修改问卷
     * @param questionnaireEntity
     * @return
     */
    @RequestMapping(value = "/modifyQuestionnaireInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyQuestionnaire(@RequestBody QuestionnaireEntity questionnaireEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            int result = questionnaireService.modifyQuestionnaireInfo(questionnaireEntity);
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
     * 删除问卷
     * @param questionnaireEntity
     * @return
     */
    @RequestMapping(value = "/deleteQuestionnaire", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteQuestionnaire(@RequestBody QuestionnaireEntity questionnaireEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            int result = questionnaireService.deleteQuestionnaire(questionnaireEntity);
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
