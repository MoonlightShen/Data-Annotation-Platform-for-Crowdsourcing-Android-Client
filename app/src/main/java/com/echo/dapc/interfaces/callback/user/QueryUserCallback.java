package com.echo.dapc.interfaces.callback.user;

import com.echo.dapc.bean.logic.User;

public interface QueryUserCallback {
    void onSuccess(User user);
    void onUserNotExist();
    void onError(String error);
}
