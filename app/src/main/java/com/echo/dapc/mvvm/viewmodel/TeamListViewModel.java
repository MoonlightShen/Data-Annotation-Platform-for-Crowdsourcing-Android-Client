package com.echo.dapc.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.mvvm.model.TeamListModel;

public class TeamListViewModel extends BaseViewModel<TeamListModel> {
    public TeamListViewModel(@NonNull Application application) {
        super(application);
    }

}
