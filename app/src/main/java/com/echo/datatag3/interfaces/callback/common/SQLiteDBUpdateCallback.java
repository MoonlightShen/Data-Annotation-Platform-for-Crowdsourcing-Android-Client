package com.echo.datatag3.interfaces.callback.common;

import com.echo.datatag3.util.system.ToastUtil;

import java.util.List;

public interface SQLiteDBUpdateCallback {
    void onSuccess();
    default void onSQLiteDBError(){
        ToastUtil.error("更新失败");
    }
}
