package com.echo.dapc.interfaces.callback.common;

import com.echo.dapc.util.system.ToastUtil;

public interface SQLiteDBUpdateCallback {
    void onSuccess();
    default void onSQLiteDBError(){
        ToastUtil.error("更新失败");
    }
}
