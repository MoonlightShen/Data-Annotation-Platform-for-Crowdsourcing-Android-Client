package com.echo.datatag3.interfaces.callback.task;

import com.echo.datatag3.bean.logic.Task;

import java.util.List;

public interface LoadAllTasksCallback {
    /**
     * 加载成功
     */
    void onSuccess(List<Task> localTasks);

    /**
     * 加载时发生错误
     */
    void onError(Object errorMessage);
}
