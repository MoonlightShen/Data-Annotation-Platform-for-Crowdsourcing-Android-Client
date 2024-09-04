package com.echo.datatag3.interfaces.callback.message;

import com.echo.datatag3.bean.entity.PrivateMessage;

import java.util.List;

public interface PrivateMessageListCallback {
    void onSuccess(List<PrivateMessage> messages, boolean hasMore);
    void onSQLiteException();
    void unknownError(String errorCode);
    void noNetwork();
}
