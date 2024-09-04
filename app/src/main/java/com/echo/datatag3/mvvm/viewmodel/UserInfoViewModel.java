package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.mvvm.model.UserInfoModel;

public class UserInfoViewModel extends BaseViewModel<UserInfoModel> {
    public UserInfoViewModel(@NonNull Application application) {
        super(application);
    }
}
