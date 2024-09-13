package com.echo.dapc.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.mvvm.model.UserInfoModel;

public class UserInfoViewModel extends BaseViewModel<UserInfoModel> {
    public UserInfoViewModel(@NonNull Application application) {
        super(application);
    }
}
