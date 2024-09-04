package com.echo.datatag3.interfaces.callback.common;

import com.echo.datatag3.util.system.ToastUtil;

import java.util.List;

public interface CommonEntityListCallback<T> {
    void onSuccess(List<T> list);
    default void onSQLiteDBError(){
        ToastUtil.toast("数据库异常");
    }
    default void onIOException(){
        ToastUtil.toast("服务器无响应");
    }
    default void unknownError(String errorCode){
        ToastUtil.info("未知错误"+errorCode);
    }
}
