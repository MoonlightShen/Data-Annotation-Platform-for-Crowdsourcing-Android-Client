package com.echo.datatag3.interfaces.callback.common;

import com.echo.datatag3.util.system.ToastUtil;

public interface CommonCallback {
    void onSuccess();
    default void onError(String error){
        ToastUtil.info("未知错误" + error);
    }
    default void onIOException(){
        ToastUtil.toast("服务器未响应");
    }
}
