package com.sisp.service;

import com.sisp.common.utils.UUIDUtil;
import com.sisp.dao.OptionEntityMapper;
import com.sisp.dao.QuestionEntityMapper;
import com.sisp.entity.OptionEntity;
import com.sisp.entity.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 问题业务逻辑层
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionEntityMapper questionEntityMapper;
    @Autowired
    private OptionEntityMapper optionEntityMapper;

    /**
     * 添加新的问题
     * @param questionEntity
     * @return
     */
    public QuestionEntity insert(QuestionEntity questionEntity) {
        questionEntity.setId(UUIDUtil.getOneUUID());
        int res = questionEntityMapper.insert(questionEntity);
        List<OptionEntity> optionEntities = questionEntity.getOption();
        if (optionEntities == null || questionEntity.getType().equals("3")) {
            return questionEntity;
        }
        for (int i = 0; i < optionEntities.size(); i++) {
            OptionEntity option = optionEntities.get(i);
            option.setOrder(i);
            option.setId(UUIDUtil.getOneUUID());
            option.setQuestionId(questionEntity.getId());
            optionEntityMapper.insert(option);
        }

        if (res == 0) {
            return null;
        }else {
            return questionEntity;
        }
    }

    /**
     * 获取问题列表
     * @param questionEntity
     * @return
     */
    public List<QuestionEntity> queryQuestionEntityList(QuestionEntity questionEntity) {
        List<QuestionEntity> questionEntityList = questionEntityMapper.queryQuestionList(questionEntity);
        for (QuestionEntity question : questionEntityList) {
            OptionEntity option = new OptionEntity();
            option.setQuestionId(question.getId());
            question.setOption(optionEntityMapper.queryOptionList(option));
        }

        return questionEntityList;
    }

    public List<QuestionEntity> queryTemplateQuestionList(QuestionEntity questionEntity) {
        List<QuestionEntity> questionEntityList = questionEntityMapper.queryTemplateQUestionList(questionEntity);
        for (QuestionEntity question : questionEntityList) {
            OptionEntity option = new OptionEntity();
            option.setQuestionId(question.getId());
            question.setOption(optionEntityMapper.queryTemplateOptionList(option));
        }

        return questionEntityList;
    }
}
