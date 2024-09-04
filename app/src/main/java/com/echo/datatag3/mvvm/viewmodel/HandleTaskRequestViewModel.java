package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.mvvm.model.HandleTaskRequestModel;

public class HandleTaskRequestViewModel extends BaseViewModel<HandleTaskRequestModel> {
    public HandleTaskRequestViewModel(@NonNull Application application) {
        super(application);
    }

}
