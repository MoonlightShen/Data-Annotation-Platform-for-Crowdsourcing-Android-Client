package com.echo.datatag3.interfaces.callback.user;

import com.echo.datatag3.bean.logic.User;

public interface QueryUserCallback {
    void onSuccess(User user);
    void onUserNotExist();
    void onError(String error);
}
