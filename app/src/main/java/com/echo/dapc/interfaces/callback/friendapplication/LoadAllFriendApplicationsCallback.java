package com.echo.dapc.interfaces.callback.friendapplication;

import com.echo.dapc.bean.entity.FriendRequest;

import java.util.List;

public interface LoadAllFriendApplicationsCallback {
    void onSuccess(List<FriendRequest> applications);
    void onSQLiteError();
    void onIOException();
}
