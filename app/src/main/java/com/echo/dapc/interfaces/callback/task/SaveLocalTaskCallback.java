package com.echo.dapc.interfaces.callback.task;

public interface SaveLocalTaskCallback {
    /**
     * 保存成功
     */
    void onSuccess();

    /**
     * 保存时发生错误
     */
    void onError(String errorMessage);
}
