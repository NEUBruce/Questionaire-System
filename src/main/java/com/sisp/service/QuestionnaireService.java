package com.sisp.service;

import com.sisp.common.utils.UUIDUtil;
import com.sisp.dao.QuestionEntityMapper;
import com.sisp.dao.QuestionnaireEntityMapper;
import com.sisp.entity.QuestionEntity;
import com.sisp.entity.QuestionnaireEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QuestionnaireService {

    @Autowired
    private QuestionnaireEntityMapper questionnaireEntityMapper;
    @Autowired
    private QuestionService questionService;

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

        List<QuestionnaireEntity> questionnaireEntityList = questionnaireEntityMapper.queryQuestionnaireList(questionnaireEntity);
        for (QuestionnaireEntity questionnaire : questionnaireEntityList) {
            QuestionEntity questionEntity = new QuestionEntity();
            questionEntity.setQuestionnaireId(questionnaire.getId());
            questionnaire.setQuestionEntityList(questionService.queryQuestionEntityList(questionEntity));
        }

        return questionnaireEntityList;
    }

    /**
     * 根据主键修改问卷的信息
     * @param questionnaireEntity
     * @return
     */
    public int modifyQuestionnaireInfo(QuestionnaireEntity questionnaireEntity) {
        return questionnaireEntityMapper.updateByPrimaryKeySelective(questionnaireEntity);
    }

    /**
     * 删除问卷，只是设置为删除状态，并不真从数据库删除
     * @param questionnaire
     * @return
     */
    public int deleteQuestionnaire(QuestionnaireEntity questionnaire) {
        return questionnaireEntityMapper.deleteQuestionnaire(questionnaire);
    }
}
