package com.echo.datatag3.interfaces.callback.user;

import com.echo.datatag3.bean.logic.User;

import java.util.List;

public interface QueryUsersCallback {
    void onSuccess(List<User> users);
    void onError(String error);
}
