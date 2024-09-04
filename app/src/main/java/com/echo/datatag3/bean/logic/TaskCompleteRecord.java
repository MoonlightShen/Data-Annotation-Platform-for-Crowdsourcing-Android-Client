package com.echo.datatag3.bean.logic;

public class TaskCompleteRecord {
    private Long requestTime;
    private Boolean complete;
    private Integer taggingQuantity;

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Integer getTaggingQuantity() {
        return taggingQuantity;
    }

    public void setTaggingQuantity(Integer taggingQuantity) {
        this.taggingQuantity = taggingQuantity;
    }

    public TaskCompleteRecord(Long requestTime, Boolean complete, Integer taggingQuantity) {
        this.requestTime = requestTime;
        this.complete = complete;
        this.taggingQuantity = taggingQuantity;
    }

    public TaskCompleteRecord() {
    }
}
