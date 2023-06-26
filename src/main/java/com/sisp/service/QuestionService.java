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

@Service
public class QuestionService {
    @Autowired
    private QuestionEntityMapper questionEntityMapper;
    @Autowired
    private OptionEntityMapper optionEntityMapper;

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
}
