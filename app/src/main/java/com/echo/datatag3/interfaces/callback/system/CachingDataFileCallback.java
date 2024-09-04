package com.echo.datatag3.interfaces.callback.system;

import com.echo.datatag3.bean.logic.DataFile;
import com.echo.datatag3.util.system.ToastUtil;

public interface CachingDataFileCallback {
    void onSuccess(DataFile dataFile);
    default void onCachingError(){
        ToastUtil.error("缓存失败");
    }
    default void onFileCorruption(){
        ToastUtil.error("文件版本不支持或已损坏");
    }
    default void onFileNotExist(){
        ToastUtil.error("文件不存在");
    }
    default void onReadError(){
        ToastUtil.error("读取失败");
    }
}
