package com.sisp.entity;

/**
 * 选择记录实体，用于存储用户选择的选项
 */
public class AnswerEntity {
    private String id;
    private String chooseTerm;
    private String recordId;
    private int questionIndex;
    private String type;
    private int row;

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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

}
