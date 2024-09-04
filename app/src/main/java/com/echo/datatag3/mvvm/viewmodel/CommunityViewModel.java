package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.mvvm.model.CommunityModel;

public class CommunityViewModel extends BaseViewModel<CommunityModel> {
    public CommunityViewModel(@NonNull Application application) {
        super(application);
    }
}
