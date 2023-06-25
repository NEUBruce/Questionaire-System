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

    public int insert(QuestionEntity questionEntity) {
        questionEntity.setId(UUIDUtil.getOneUUID());
        int res = questionEntityMapper.insert(questionEntity);
        List<OptionEntity> optionEntities = questionEntity.getOption();
        if (optionEntities == null || questionEntity.getType().equals("3")) {
            return res;
        }
        for (OptionEntity option : optionEntities) {
            option.setId(UUIDUtil.getOneUUID());
            option.setQuestionId(questionEntity.getId());
            int tmp = optionEntityMapper.insert(option);
            if (tmp == 0) {
                res = 0;
            }
        }

        return res;
    }
}
