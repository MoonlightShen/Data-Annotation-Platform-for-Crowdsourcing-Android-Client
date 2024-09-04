package com.echo.datatag3.interfaces.callback.common;

import android.widget.Toast;

import com.echo.datatag3.util.system.ToastUtil;

import java.util.List;

public interface SQLiteDBQueryCallback<T> {
    void onSuccess(List<T> list);
    default void onSQLiteDBError(){
        ToastUtil.error("加载失败");
    }
}
