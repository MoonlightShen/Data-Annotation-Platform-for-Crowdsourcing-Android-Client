package com.echo.datatag3.interfaces.callback.message;

import com.echo.datatag3.util.network.response.message.MessageInfoRes;

public interface SendMessageCallback {
    void onSuccess(MessageInfoRes .Data data);
    void unknownError(String errorCode);
}
