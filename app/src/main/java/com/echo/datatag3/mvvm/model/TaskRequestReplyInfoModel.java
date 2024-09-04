package com.echo.datatag3.mvvm.model;

import androidx.databinding.Bindable;

import com.echo.datatag3.BR;
import com.echo.datatag3.base.BaseModel;

public class TaskRequestReplyInfoModel extends BaseModel {
    private boolean adopt;
    private String applyContent;
    private long remainingTime;

    @Bindable
    public boolean isAdopt() {
        return adopt;
    }

    public void setAdopt(boolean adopt) {
        this.adopt = adopt;
        notifyPropertyChanged(BR.adopt);
    }

    @Bindable
    public long getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;
        notifyPropertyChanged(BR.remainingTime);
    }

    @Bindable
    public String getApplyContent() {
        return applyContent;
    }

    public void setApplyContent(String applyContent) {
        this.applyContent = applyContent;
        notifyPropertyChanged(BR.applyContent);
    }
}
