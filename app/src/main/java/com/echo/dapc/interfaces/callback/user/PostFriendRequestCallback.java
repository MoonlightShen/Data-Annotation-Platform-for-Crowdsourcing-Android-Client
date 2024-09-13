package com.echo.dapc.interfaces.callback.user;

public interface PostFriendRequestCallback {
    void onSuccess();
    void onError(String code);
}
