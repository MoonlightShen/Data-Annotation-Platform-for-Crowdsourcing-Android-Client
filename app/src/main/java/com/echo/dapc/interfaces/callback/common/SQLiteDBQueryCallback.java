package com.echo.dapc.interfaces.callback.common;

import com.echo.dapc.util.system.ToastUtil;

import java.util.List;

public interface SQLiteDBQueryCallback<T> {
    void onSuccess(List<T> list);
    default void onSQLiteDBError(){
        ToastUtil.normal("加载失败");
    }
}
