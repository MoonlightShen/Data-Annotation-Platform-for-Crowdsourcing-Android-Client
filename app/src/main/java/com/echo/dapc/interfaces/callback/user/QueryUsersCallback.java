package com.echo.dapc.interfaces.callback.user;

import com.echo.dapc.bean.logic.User;

import java.util.List;

public interface QueryUsersCallback {
    void onSuccess(List<User> users);
    void onError(String error);
}
