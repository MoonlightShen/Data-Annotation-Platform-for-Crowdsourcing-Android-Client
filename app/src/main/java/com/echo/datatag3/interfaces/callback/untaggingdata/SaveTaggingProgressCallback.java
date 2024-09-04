package com.echo.datatag3.interfaces.callback.untaggingdata;

import com.echo.datatag3.util.system.ToastUtil;

public interface SaveTaggingProgressCallback {
    void onSuccess();
    void setNotExist();
    void currentDataIndexOutOfRange();
    default void onSaveDataError(){
        ToastUtil.error("数据库异常保存失败");
    }
    default void onSaveSetError(){
        ToastUtil.error("数据库异常保存失败");
    }
}
