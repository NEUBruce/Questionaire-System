package com.sisp.controller;

import com.sisp.beans.HttpResponseEntity;
import com.sisp.entity.QuestionEntity;
import com.sisp.entity.RecordEntity;
import com.sisp.service.QuestionService;
import com.sisp.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecordController {
    @Autowired
    RecordService recordService;

    @RequestMapping(value = "/addRecord", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addQuestion(@RequestBody RecordEntity recordEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            int result = recordService.insert(recordEntity);
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

    @RequestMapping(value = "/queryRecordList", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryQuestionList(@RequestBody RecordEntity recordEntity) {
        HttpResponseEntity httpResponse = new HttpResponseEntity();
        try {
            List<RecordEntity> result = recordService.queryRecordEntityList(recordEntity);
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
