package com.sisp.entity;

import java.util.Date;
import java.util.List;

/**
 * 问卷实体类
 */
public class QuestionnaireEntity {
    private String id;
    private String projectId;
    private String questionnaireName;
    private String questionnaireDescription;
    private String createdBy;
    private Date creationDate;
    private String lastUpdatedBy;
    private Date lastUpdateDate;
    private String status;
    private Date startTime;
    private Date stopTime;
    private String type;
    private int answerTimeLimit;
    private String style;
    private String target;
    private String group;

    private List<QuestionEntity> questionEntityList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getQuestionnaireName() {
        return questionnaireName;
    }

    public void setQuestionnaireName(String questionnaireName) {
        this.questionnaireName = questionnaireName;
    }

    public String getQuestionnaireDescription() {
        return questionnaireDescription;
    }

    public void setQuestionnaireDescription(String questionnaireDescription) {
        this.questionnaireDescription = questionnaireDescription;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<QuestionEntity> getQuestionEntityList() {
        return questionEntityList;
    }

    public void setQuestionEntityList(List<QuestionEntity> questionEntityList) {
        this.questionEntityList = questionEntityList;
    }

    public int getAnswerTimeLimit() {
        return answerTimeLimit;
    }

    public void setAnswerTimeLimit(int answerTimeLimit) {
        this.answerTimeLimit = answerTimeLimit;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
