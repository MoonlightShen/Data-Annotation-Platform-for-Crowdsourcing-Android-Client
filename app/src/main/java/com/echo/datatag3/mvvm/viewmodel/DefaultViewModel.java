package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.mvvm.model.DefaultModel;

public class DefaultViewModel extends BaseViewModel<DefaultModel> {
    public DefaultViewModel(@NonNull Application application) {
        super(application);
    }
}
