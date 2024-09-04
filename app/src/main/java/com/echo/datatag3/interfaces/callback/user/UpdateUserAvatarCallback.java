package com.echo.datatag3.interfaces.callback.user;

public interface UpdateUserAvatarCallback {
    void onSuccess();
    void onTransmissionError();
    void onError(String error);
    void onIOException(String error);
}
