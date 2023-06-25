package com.sisp.service;

import com.sisp.common.utils.UUIDUtil;
import com.sisp.dao.QuestionnaireEntityMapper;
import com.sisp.entity.QuestionnaireEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QuestionnaireService {

    @Autowired
    private QuestionnaireEntityMapper questionnaireEntityMapper;

    public QuestionnaireEntity addQuestionnaire(QuestionnaireEntity questionnaireEntity) {
        questionnaireEntity.setCreationDate(new Date());
        questionnaireEntity.setLastUpdateDate(new Date());
        questionnaireEntity.setId(UUIDUtil.getOneUUID());
        questionnaireEntity.setStatus("0");

        int res = questionnaireEntityMapper.insert(questionnaireEntity);
        if (res == 0) {
            return null;
        }else {
            return questionnaireEntity;
        }
    }
}
