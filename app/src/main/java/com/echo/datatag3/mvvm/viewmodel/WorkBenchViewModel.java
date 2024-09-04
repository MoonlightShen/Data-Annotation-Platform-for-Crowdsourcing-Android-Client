package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.mvvm.model.WorkBenchModel;

public class WorkBenchViewModel extends BaseViewModel<WorkBenchModel> {
    public WorkBenchViewModel(@NonNull Application application) {
        super(application);
    }

    public void search() {

    }
}
