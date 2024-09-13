package com.echo.dapc.interfaces.callback.common;

import com.echo.dapc.util.system.ToastUtil;

public interface CommonEntityCallback<T> {
    void onSuccess(T t);
    default void onSQLiteDBError(){
        ToastUtil.normal("数据库异常");
    }
    default void onIOException(){
        ToastUtil.normal("服务器无响应");
    }
    default void unknownError(String errorCode){
        ToastUtil.info("未知错误"+errorCode);
    }
}
