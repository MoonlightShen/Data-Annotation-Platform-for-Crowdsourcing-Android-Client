package com.echo.dapc.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.mvvm.model.HandleTaskRequestModel;

public class HandleTaskRequestViewModel extends BaseViewModel<HandleTaskRequestModel> {
    public HandleTaskRequestViewModel(@NonNull Application application) {
        super(application);
    }

}
