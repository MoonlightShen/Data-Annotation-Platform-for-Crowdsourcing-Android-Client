package com.echo.dapc.interfaces.callback.chatinfo;

import com.echo.dapc.util.network.response.message.UnreadMessageInfoRes;

public interface ChatInfoListCallback {
    void onSuccess(UnreadMessageInfoRes.Data data);
    void onSQLiteException();
    void unknownError(String errorCode);
    void onNoNetwork();
}
