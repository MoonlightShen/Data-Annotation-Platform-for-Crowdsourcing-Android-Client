package com.echo.datatag3.interfaces.callback.task;

public interface UploadTaskCallback {
    void onSuccess(Long taskId);

    void unknownError(String errorCode);

    void onIOException();
}
