package com.echo.dapc.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.mvvm.model.DefaultModel;

public class DefaultViewModel extends BaseViewModel<DefaultModel> {
    public DefaultViewModel(@NonNull Application application) {
        super(application);
    }
}
