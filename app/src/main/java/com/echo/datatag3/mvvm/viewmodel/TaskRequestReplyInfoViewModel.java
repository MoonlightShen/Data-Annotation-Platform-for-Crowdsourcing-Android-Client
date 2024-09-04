package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.mvvm.model.TaskRequestReplyInfoModel;

public class TaskRequestReplyInfoViewModel extends BaseViewModel<TaskRequestReplyInfoModel> {
    public TaskRequestReplyInfoViewModel(@NonNull Application application) {
        super(application);
    }

}
