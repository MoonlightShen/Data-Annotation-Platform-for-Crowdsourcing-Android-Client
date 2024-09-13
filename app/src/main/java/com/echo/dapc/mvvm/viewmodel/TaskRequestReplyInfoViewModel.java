package com.echo.dapc.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.mvvm.model.TaskRequestReplyInfoModel;

public class TaskRequestReplyInfoViewModel extends BaseViewModel<TaskRequestReplyInfoModel> {
    public TaskRequestReplyInfoViewModel(@NonNull Application application) {
        super(application);
    }

}
