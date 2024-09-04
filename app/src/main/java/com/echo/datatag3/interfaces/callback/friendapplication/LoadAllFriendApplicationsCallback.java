package com.echo.datatag3.interfaces.callback.friendapplication;

import com.echo.datatag3.bean.entity.FriendRequest;

import java.util.List;

public interface LoadAllFriendApplicationsCallback {
    void onSuccess(List<FriendRequest> applications);
    void onSQLiteError();
    void onIOException();
}
