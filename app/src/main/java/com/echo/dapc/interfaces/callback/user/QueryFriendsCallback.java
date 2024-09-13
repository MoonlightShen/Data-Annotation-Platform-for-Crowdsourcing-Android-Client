package com.echo.dapc.interfaces.callback.user;

import com.echo.dapc.bean.entity.Friend;

import java.util.List;

public interface QueryFriendsCallback {
    void onSuccess(List<Friend> friends);

    void onError();
}
