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

@RestController
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;

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


    // 查询用户列表
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


}
