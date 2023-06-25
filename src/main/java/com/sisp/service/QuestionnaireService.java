package com.sisp.service;

import com.sisp.common.utils.UUIDUtil;
import com.sisp.dao.QuestionnaireEntityMapper;
import com.sisp.entity.QuestionnaireEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QuestionnaireService {

    @Autowired
    private QuestionnaireEntityMapper questionnaireEntityMapper;

    /**
     * 添加问卷
     * @param questionnaireEntity
     * @return
     */
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

    /**
     * 根据条件查找问卷列表
     * @param questionnaireEntity
     * @return
     */
    public List<QuestionnaireEntity> queryQuestionnaireList(QuestionnaireEntity questionnaireEntity) {

        return questionnaireEntityMapper.queryQuestionnaireList(questionnaireEntity);
    }
}
