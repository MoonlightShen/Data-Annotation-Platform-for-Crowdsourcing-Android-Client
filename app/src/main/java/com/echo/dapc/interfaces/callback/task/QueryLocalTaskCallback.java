package com.echo.dapc.interfaces.callback.task;

import com.echo.dapc.bean.entity.LocalTask;

public interface QueryLocalTaskCallback {
    /**
     * 查询成功，为null时表示没查到
     * @param task
     */
    void onSuccess(LocalTask task);

    /**
     * 查询时发生错误
     * @param errorMessage
     */
    void onError(Object errorMessage);
}
