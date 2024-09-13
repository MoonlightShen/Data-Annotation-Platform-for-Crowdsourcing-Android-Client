package com.echo.dapc.interfaces.callback.task;

import com.echo.dapc.bean.entity.LocalTask;

import java.util.List;

public interface LoadAllLocalTasksCallback {
    void onSuccess(List<LocalTask> localTasks);
    void onError(Object errorMessage);
}
