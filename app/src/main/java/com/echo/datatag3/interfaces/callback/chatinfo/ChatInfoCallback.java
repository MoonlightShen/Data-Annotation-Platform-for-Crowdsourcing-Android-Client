package com.echo.datatag3.interfaces.callback.chatinfo;

import com.echo.datatag3.bean.entity.ChatInfo;

public interface ChatInfoCallback {
    void onSuccess(ChatInfo chatInfo);
    void onSQLiteException();
    void onError(String errorCode);
}
