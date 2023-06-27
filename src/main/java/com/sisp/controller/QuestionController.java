package com.sisp.controller;

import com.sisp.beans.HttpResponseEntity;
import com.sisp.entity.QuestionEntity;
import com.sisp.entity.QuestionnaireEntity;
import com.sisp.service.QuestionService;
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
public class QuestionController {
    @Autowired
    QuestionService questionService;

    /**
     * 添加问题
     * @param questionEntity
     * @return
     */
    @RequestMapping(value = "/addQuestion", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addQuestion(@RequestBody QuestionEntity questionEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            QuestionEntity result = questionService.insert(questionEntity);
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
     * 查找模板题
     * @param questionEntity
     * @return
     */
    @RequestMapping(value = "/queryTemplateQuestionList", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryTemplateQuestionList(@RequestBody QuestionEntity questionEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            List<QuestionEntity> result = questionService.queryTemplateQuestionList(questionEntity);
            if (result == null) {
                httpResponse.setCode("0");
                httpResponse.setData(0);
                httpResponse.setMessage("查询失败!");
            }else {
                httpResponse.setCode("666");
                httpResponse.setData(result);
                httpResponse.setMessage("查询成功!");
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }

        return httpResponse;

    }

    /**
     * 查找题目
     * @param questionEntity
     * @return
     */
    @RequestMapping(value = "/queryQuestionList", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryQuestionList(@RequestBody QuestionEntity questionEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            List<QuestionEntity> result = questionService.queryQuestionEntityList(questionEntity);
            if (result == null) {
                httpResponse.setCode("0");
                httpResponse.setData(0);
                httpResponse.setMessage("查询失败!");
            }else {
                httpResponse.setCode("666");
                httpResponse.setData(result);
                httpResponse.setMessage("查询成功!");
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }

        return httpResponse;

    }
}
