package com.echo.dapc.mvvm.model;

import androidx.databinding.Bindable;

import com.echo.dapc.BR;
import com.echo.dapc.base.BaseModel;
import com.echo.dapc.bean.entity.FriendRequest;

public class FriendRequestModel extends BaseModel {
    private FriendRequest request;
    private String nickname;
    private boolean mineRequest=false;

    private String reply;

    @Bindable
    public FriendRequest getRequest() {
        return request;
    }

    public void setRequest(FriendRequest request) {
        this.request = request;
        notifyPropertyChanged(BR.request);
    }

    @Bindable
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        notifyPropertyChanged(BR.nickname);
    }

    @Bindable
    public boolean isMineRequest() {
        return mineRequest;
    }

    public void setMineRequest(boolean mineRequest) {
        this.mineRequest = mineRequest;
        notifyPropertyChanged(BR.mineRequest);
    }

    @Bindable
    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
        notifyPropertyChanged(BR.reply);
    }
}
