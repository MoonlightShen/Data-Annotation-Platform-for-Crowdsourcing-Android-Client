package com.echo.dapc.interfaces.callback.common;

import java.util.List;

public interface RequestCallback<T> {
    void onSuccess(List<T> list);
    void unknownError(String errorCode);
    void noNetWork();
}
