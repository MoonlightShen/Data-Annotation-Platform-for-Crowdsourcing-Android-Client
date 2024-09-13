package com.echo.dapc.interfaces.callback.task;

import com.echo.dapc.bean.logic.Task;

import java.util.List;

public interface LoadRecommendationTasksCallback {
    void onSuccess(List<Task> tasks);

    void onError(String errorMessage);
}
