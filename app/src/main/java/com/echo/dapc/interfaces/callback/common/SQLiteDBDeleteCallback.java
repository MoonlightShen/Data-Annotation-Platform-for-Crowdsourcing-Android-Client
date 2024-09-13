package com.echo.dapc.interfaces.callback.common;

import com.echo.dapc.util.system.ToastUtil;

public interface SQLiteDBDeleteCallback {
    void onSuccess();
    void onSQLiteError();
    default void onEntityNotExist(){
        ToastUtil.normal("实体不存在");
    }
}
