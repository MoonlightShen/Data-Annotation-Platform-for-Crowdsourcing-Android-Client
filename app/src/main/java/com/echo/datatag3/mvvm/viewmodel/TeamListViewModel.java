package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.mvvm.model.TeamListModel;

public class TeamListViewModel extends BaseViewModel<TeamListModel> {
    public TeamListViewModel(@NonNull Application application) {
        super(application);
    }

}
