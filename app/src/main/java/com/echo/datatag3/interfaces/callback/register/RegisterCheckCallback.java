package com.echo.datatag3.interfaces.callback.register;

public interface RegisterCheckCallback {
    void onAccountExist();
    void onPhoneExist();
    void onEmailExist();
    void onSuccess();
    void onFailure(String code);
    void onNetworkError(String errorMessage);
}
