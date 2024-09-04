package com.echo.datatag3.mvvm.viewmodel.taskinfo;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.mvvm.model.task.TaskCompletedInfoModel;

public class TaskCompletedInfoViewModel extends BaseViewModel<TaskCompletedInfoModel> {
    public TaskCompletedInfoViewModel(@NonNull Application application) {
        super(application);
    }

}
