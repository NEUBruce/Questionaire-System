package com.sisp.entity;

/**
 * 选项实体，存储题目的选项
 */
public class OptionEntity {
    private String id;
    private String chooseTerm;
    private int fraction;
    private String questionId;
    private int order;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChooseTerm() {
        return chooseTerm;
    }

    public void setChooseTerm(String chooseTerm) {
        this.chooseTerm = chooseTerm;
    }

    public int getFraction() {
        return fraction;
    }

    public void setFraction(int fraction) {
        this.fraction = fraction;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
