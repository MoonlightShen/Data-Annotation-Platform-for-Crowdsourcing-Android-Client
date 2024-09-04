package com.echo.datatag3.interfaces.callback.common;

import com.echo.datatag3.util.system.ToastUtil;

public interface SQLiteDBDeleteCallback {
    void onSuccess();
    void onSQLiteError();
    default void onEntityNotExist(){
        ToastUtil.toast("实体不存在");
    }
}
