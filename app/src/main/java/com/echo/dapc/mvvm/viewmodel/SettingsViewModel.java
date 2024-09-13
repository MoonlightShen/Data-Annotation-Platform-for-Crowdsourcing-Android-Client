package com.echo.dapc.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.mvvm.model.SettingsModel;

public class SettingsViewModel extends BaseViewModel<SettingsModel> {
    public SettingsViewModel(@NonNull Application application) {
        super(application);
    }

}
