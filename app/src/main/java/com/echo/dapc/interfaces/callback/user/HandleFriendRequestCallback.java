package com.echo.dapc.interfaces.callback.user;

public interface HandleFriendRequestCallback {
    void onAgree();
    void onIgnore();
    void onRefuse();
    void onError(String code);
}
