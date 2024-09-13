package com.echo.dapc.interfaces.callback.user;

public interface QueryUserNicknameCallback {
    void onSuccess(String nickname);
    void onUserNotExist();
    void onError(String error);
}
