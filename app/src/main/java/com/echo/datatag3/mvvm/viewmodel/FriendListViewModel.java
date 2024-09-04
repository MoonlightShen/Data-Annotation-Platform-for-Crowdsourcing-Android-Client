package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.mvvm.model.FriendListModel;

public class FriendListViewModel extends BaseViewModel<FriendListModel> {
    public FriendListViewModel(@NonNull Application application) {
        super(application);
    }
}
