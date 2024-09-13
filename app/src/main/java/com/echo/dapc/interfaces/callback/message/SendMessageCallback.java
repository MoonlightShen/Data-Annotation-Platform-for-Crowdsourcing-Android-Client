package com.echo.dapc.interfaces.callback.message;

import com.echo.dapc.util.network.response.message.MessageInfoRes;

public interface SendMessageCallback {
    void onSuccess(MessageInfoRes .Data data);
    void unknownError(String errorCode);
}
