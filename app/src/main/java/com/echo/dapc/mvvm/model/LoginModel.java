package com.echo.dapc.mvvm.model;

import androidx.databinding.Bindable;

import com.echo.dapc.BR;
import com.echo.dapc.base.BaseModel;

public class LoginModel extends BaseModel {
    private static boolean logging=false;
    private String account;//账号
    private String password;//密码
    private boolean consentPolicy;//同意政策

    public boolean isLogging() {
        return logging;
    }

    public void setLogging(boolean logging) {
        LoginModel.logging = logging;
    }

    @Bindable
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
        notifyPropertyChanged(BR.account);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public boolean isConsentPolicy() {
        return consentPolicy;
    }

    public void setConsentPolicy(boolean consentPolicy) {
        this.consentPolicy = consentPolicy;
        notifyPropertyChanged(BR.consentPolicy);
    }
}
