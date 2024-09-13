package com.echo.dapc.interfaces.callback.task;

public interface UploadTaskCallback {
    void onSuccess(Long taskId);

    void unknownError(String errorCode);

    void onIOException();
}
