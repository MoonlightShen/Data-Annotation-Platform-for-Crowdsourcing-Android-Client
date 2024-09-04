package com.echo.datatag3.interfaces.callback.chatinfo;

import com.echo.datatag3.util.network.response.message.UnreadMessageInfoRes;

public interface ChatInfoListCallback {
    void onSuccess(UnreadMessageInfoRes.Data data);
    void onSQLiteException();
    void unknownError(String errorCode);
    void onNoNetwork();
}
