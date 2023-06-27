package com.sisp.entity;

import java.util.List;

/**
 * 题目实体，用于存储问卷的题目
 */
public class QuestionEntity {
    private String id;
    private String problemName;
    private String type;
    private String leftTitle;
    private boolean mustAnswer;
    private List<OptionEntity> option;
    private String questionnaireId;
    private int Order;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLeftTitle() {
        return leftTitle;
    }

    public void setLeftTitle(String leftTitle) {
        this.leftTitle = leftTitle;
    }

    public boolean isMustAnswer() {
        return mustAnswer;
    }

    public void setMustAnswer(boolean mustAnswer) {
        this.mustAnswer = mustAnswer;
    }

    public List<OptionEntity> getOption() {
        return option;
    }

    public void setOption(List<OptionEntity> option) {
        this.option = option;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int order) {
        Order = order;
    }

}
