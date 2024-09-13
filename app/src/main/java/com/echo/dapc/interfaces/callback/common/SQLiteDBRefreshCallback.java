package com.echo.dapc.interfaces.callback.common;

public interface SQLiteDBRefreshCallback<T> {
    void onSuccess(T t);
    void onSQLiteError();
}
