package com.echo.datatag3.interfaces.callback.task;

import com.echo.datatag3.bean.logic.Task;

import java.util.List;

public interface TaskListCallback {
    void onSuccess(List<Task> tasks, boolean hasMore);

    void unknownError(String errorCode);

    void onIOException();

    void onSQLiteError();
}
