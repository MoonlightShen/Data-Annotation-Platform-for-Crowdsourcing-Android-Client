package com.echo.dapc.interfaces.callback.message;

import com.echo.dapc.bean.entity.PrivateMessage;

public interface PrivateMessageCallback {
    void onSuccess(PrivateMessage message);
    void onSQLiteException();
    void onError(String errorCode);
}
