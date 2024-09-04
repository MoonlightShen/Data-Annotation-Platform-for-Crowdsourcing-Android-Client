package com.echo.datatag3.interfaces.callback.common;

public interface SQLiteDBRefreshCallback<T> {
    void onSuccess(T t);
    void onSQLiteError();
}
