package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.mvvm.model.FriendRequestCreateModel;

public class FriendRequestCreateViewModel extends BaseViewModel<FriendRequestCreateModel> {
    public FriendRequestCreateViewModel(@NonNull Application application) {
        super(application);
    }
}
