package com.echo.datatag3.interfaces.callback.task;

import com.echo.datatag3.bean.logic.Task;

import java.util.List;

public interface LoadRecommendationTasksCallback {
    void onSuccess(List<Task> tasks);

    void onError(String errorMessage);
}
