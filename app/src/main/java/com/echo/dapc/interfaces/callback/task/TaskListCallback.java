package com.echo.dapc.interfaces.callback.task;

import com.echo.dapc.bean.logic.Task;

import java.util.List;

public interface TaskListCallback {
    void onSuccess(List<Task> tasks, boolean hasMore);

    void unknownError(String errorCode);

    void onIOException();

    void onSQLiteError();
}
