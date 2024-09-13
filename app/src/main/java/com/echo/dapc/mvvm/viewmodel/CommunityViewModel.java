package com.echo.dapc.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.mvvm.model.CommunityModel;

public class CommunityViewModel extends BaseViewModel<CommunityModel> {
    public CommunityViewModel(@NonNull Application application) {
        super(application);
    }
}
