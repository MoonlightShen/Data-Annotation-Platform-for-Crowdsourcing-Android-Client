package com.echo.dapc.interfaces.callback.task;

import com.echo.dapc.bean.logic.Task;

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
