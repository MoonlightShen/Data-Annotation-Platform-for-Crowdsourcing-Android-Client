package com.echo.dapc.mvvm.model;

import androidx.databinding.Bindable;

import com.echo.dapc.BR;
import com.echo.dapc.base.BaseModel;
import com.echo.dapc.bean.logic.User;

public class FriendRequestCreateModel extends BaseModel {
    private User user;
    private String validationContent;

    @Bindable
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        notifyPropertyChanged(BR.user);
    }

    @Bindable
    public String getValidationContent() {
        return validationContent;
    }

    public void setValidationContent(String validationContent) {
        this.validationContent = validationContent;
        notifyPropertyChanged(BR.validationContent);
    }
}
