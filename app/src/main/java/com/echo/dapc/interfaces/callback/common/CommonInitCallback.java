package com.echo.dapc.interfaces.callback.common;

import java.util.List;

public interface CommonInitCallback <T>{
    void onSuccess(List<T> list, boolean moreInServer);
    void noNetWork();
    void onSQLiteDBError();
    void onMangoDBError();
}
