package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.mvvm.model.SettingsModel;

public class SettingsViewModel extends BaseViewModel<SettingsModel> {
    public SettingsViewModel(@NonNull Application application) {
        super(application);
    }

}
