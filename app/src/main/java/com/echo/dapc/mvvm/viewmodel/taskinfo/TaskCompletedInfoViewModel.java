package com.echo.dapc.mvvm.viewmodel.taskinfo;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.mvvm.model.task.TaskCompletedInfoModel;

public class TaskCompletedInfoViewModel extends BaseViewModel<TaskCompletedInfoModel> {
    public TaskCompletedInfoViewModel(@NonNull Application application) {
        super(application);
    }

}
