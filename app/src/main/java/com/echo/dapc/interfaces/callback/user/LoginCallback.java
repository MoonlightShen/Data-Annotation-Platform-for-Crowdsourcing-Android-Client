package com.echo.dapc.interfaces.callback.user;

import androidx.annotation.NonNull;

public interface LoginCallback {
    void onSuccess(Long userId,Integer point, String token);
    void onAccountNotExist();
    void onPasswordError();
    void onRequestError();
    void onErrorWithCode(@NonNull String code);
    void onIOException(@NonNull String error);
}
