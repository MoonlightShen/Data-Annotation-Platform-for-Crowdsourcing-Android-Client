package com.echo.datatag3.interfaces.callback.user;

import com.echo.datatag3.bean.entity.Friend;

import java.util.List;

public interface QueryFriendsCallback {
    void onSuccess(List<Friend> friends);

    void onError();
}
