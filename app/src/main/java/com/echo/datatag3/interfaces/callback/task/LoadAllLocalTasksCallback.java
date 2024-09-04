package com.echo.datatag3.interfaces.callback.task;

import com.echo.datatag3.bean.entity.LocalTask;

import java.util.List;

public interface LoadAllLocalTasksCallback {
    void onSuccess(List<LocalTask> localTasks);
    void onError(Object errorMessage);
}
