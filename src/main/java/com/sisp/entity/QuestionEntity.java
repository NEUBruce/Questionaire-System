package com.sisp.entity;

import java.util.List;

public class QuestionEntity {
    private String id;
    private String problemName;
    private String type;
    private String leftTitle;
    private boolean mustAnswer;
    private List<OptionEntity> option;

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
}
