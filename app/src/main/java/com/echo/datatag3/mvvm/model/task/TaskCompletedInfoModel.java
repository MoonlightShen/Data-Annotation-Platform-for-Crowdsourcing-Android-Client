package com.echo.datatag3.mvvm.model.task;

import androidx.databinding.Bindable;

import com.echo.datatag3.BR;
import com.echo.datatag3.base.BaseModel;

import java.util.Set;

public class TaskCompletedInfoModel extends BaseModel {
    private String title;
    private Long startTime;
    private Long endTime;
    private Integer totalData;
    private Integer completionRate;
    private Integer remainingPoint;
    private Set<Long> applyUsers;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public Set<Long> getApplyUsers() {
        return applyUsers;
    }

    public void setApplyUsers(Set<Long> applyUsers) {
        this.applyUsers = applyUsers;
        notifyPropertyChanged(BR.applyUsers);
    }

    @Bindable
    public Integer getRemainingPoint() {
        return remainingPoint;
    }

    public void setRemainingPoint(Integer remainingPoint) {
        this.remainingPoint = remainingPoint;
        notifyPropertyChanged(BR.remainingPoint);
    }

    @Bindable
    public Integer getTotalData() {
        return totalData;
    }

    @Bindable
    public Integer getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Integer completionRate) {
        this.completionRate = completionRate;
        notifyPropertyChanged(BR.completionRate);
    }

    public void setTotalData(Integer totalData) {
        this.totalData = totalData;
        notifyPropertyChanged(BR.totalData);
    }

    @Bindable
    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
        notifyPropertyChanged(BR.startTime);
    }

    @Bindable
    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
        notifyPropertyChanged(BR.endTime);
    }
}
