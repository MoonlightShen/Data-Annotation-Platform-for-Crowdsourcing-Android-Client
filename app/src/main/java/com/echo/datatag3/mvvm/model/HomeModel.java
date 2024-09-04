package com.echo.datatag3.mvvm.model;

import androidx.databinding.Bindable;

import com.echo.datatag3.BR;
import com.echo.datatag3.base.BaseModel;

public class HomeModel extends BaseModel {
    private String userName;
    private Integer unReadNoticeNum;

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public Integer getUnReadNoticeNum() {
        return unReadNoticeNum;
    }

    public void setUnReadNoticeNum(Integer unReadNoticeNum) {
        this.unReadNoticeNum = unReadNoticeNum;
        notifyPropertyChanged(BR.unReadNoticeNum);
    }
}
