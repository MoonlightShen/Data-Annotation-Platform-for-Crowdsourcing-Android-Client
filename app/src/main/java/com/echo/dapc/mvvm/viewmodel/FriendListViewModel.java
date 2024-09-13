package com.echo.dapc.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.mvvm.model.FriendListModel;

public class FriendListViewModel extends BaseViewModel<FriendListModel> {
    public FriendListViewModel(@NonNull Application application) {
        super(application);
    }
}
