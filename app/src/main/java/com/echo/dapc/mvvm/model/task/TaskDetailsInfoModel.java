package com.echo.dapc.mvvm.model.task;

import androidx.databinding.Bindable;

import com.echo.dapc.BR;
import com.echo.dapc.base.BaseModel;

public class TaskDetailsInfoModel extends BaseModel {
    private Long ownerId;
    private String ownerNickname;
    private Long taskId;

    private String title;
    private String description;
    private Long remainingTime;
    private Integer remainingQuota;
    private Integer groupSize;
    private Integer unitPoint;
    private Integer likeNum;
    private Integer starNum;
    private Integer readNum;
    private boolean hasLike;
    private boolean hasStar;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Bindable
    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
        notifyPropertyChanged(BR.ownerId);
    }

    @Bindable
    public String getOwnerNickname() {
        return ownerNickname;
    }

    public void setOwnerNickname(String ownerNickname) {
        this.ownerNickname = ownerNickname;
        notifyPropertyChanged(BR.ownerNickname);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public Long getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(Long remainingTime) {
        this.remainingTime = remainingTime;
        notifyPropertyChanged(BR.remainingTime);
    }

    @Bindable
    public Integer getRemainingQuota() {
        return remainingQuota;
    }

    public void setRemainingQuota(Integer remainingQuota) {
        this.remainingQuota = remainingQuota;
        notifyPropertyChanged(BR.remainingQuota);
    }

    @Bindable
    public Integer getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
        notifyPropertyChanged(BR.groupSize);
    }

    @Bindable
    public Integer getUnitPoint() {
        return unitPoint;
    }

    public void setUnitPoint(Integer unitPoint) {
        this.unitPoint = unitPoint;
        notifyPropertyChanged(BR.unitPoint);
    }

    @Bindable
    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
        notifyPropertyChanged(BR.likeNum);
    }

    @Bindable
    public Integer getStarNum() {
        return starNum;
    }

    public void setStarNum(Integer starNum) {
        this.starNum = starNum;
        notifyPropertyChanged(BR.starNum);
    }

    @Bindable
    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
        notifyPropertyChanged(BR.readNum);
    }

    public boolean isHasLike() {
        return hasLike;
    }

    public void setHasLike(boolean hasLike) {
        this.hasLike = hasLike;
    }

    public boolean isHasStar() {
        return hasStar;
    }

    public void setHasStar(boolean hasStar) {
        this.hasStar = hasStar;
    }

    public void addLike(){
        if (this.likeNum == null){
            this.likeNum = 0;
        }else {
            this.likeNum++;
        }
    }

    public void addStar(){
        if (this.starNum == null){
            this.starNum = 0;
        }else {
            this.starNum++;
        }
    }

    public void reduceLike(){
        if (this.likeNum == null){
            this.likeNum = 0;
        }else {
            this.likeNum--;
        }
    }

    public void reduceStar(){
        if (this.starNum == null){
            this.starNum = 0;
        }else {
            this.starNum--;
        }
    }
}
