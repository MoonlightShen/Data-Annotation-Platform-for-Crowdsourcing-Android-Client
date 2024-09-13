package com.echo.dapc.interfaces.callback.chatinfo;

import com.echo.dapc.bean.entity.ChatInfo;

public interface ChatInfoCallback {
    void onSuccess(ChatInfo chatInfo);
    void onSQLiteException();
    void onError(String errorCode);
}
