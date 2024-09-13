package com.echo.dapc.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.mvvm.model.WorkBenchModel;

public class WorkBenchViewModel extends BaseViewModel<WorkBenchModel> {
    public WorkBenchViewModel(@NonNull Application application) {
        super(application);
    }

    public void search() {

    }
}
