package com.echo.dapc.mvvm.model;

import androidx.databinding.Bindable;

import com.echo.dapc.BR;
import com.echo.dapc.base.BaseModel;

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
