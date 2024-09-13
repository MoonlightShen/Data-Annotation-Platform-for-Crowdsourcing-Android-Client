package com.echo.dapc.interfaces.callback.common;

import com.echo.dapc.util.system.ToastUtil;

public interface CommonCallback {
    void onSuccess();
    default void onError(String error){
        ToastUtil.info("未知错误" + error);
    }
    default void onIOException(){
        ToastUtil.normal("服务器未响应");
    }
}
