package com.echo.datatag3.interfaces.callback.message;

import com.echo.datatag3.bean.entity.PrivateMessage;

public interface PrivateMessageCallback {
    void onSuccess(PrivateMessage message);
    void onSQLiteException();
    void onError(String errorCode);
}
