package com.echo.dapc.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.mvvm.model.FriendRequestCreateModel;

public class FriendRequestCreateViewModel extends BaseViewModel<FriendRequestCreateModel> {
    public FriendRequestCreateViewModel(@NonNull Application application) {
        super(application);
    }
}
