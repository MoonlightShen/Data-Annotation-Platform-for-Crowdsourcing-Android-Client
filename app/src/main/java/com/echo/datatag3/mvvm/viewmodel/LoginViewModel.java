package com.echo.datatag3.mvvm.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseRequest;
import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.interfaces.callback.user.LoginCallback;
import com.echo.datatag3.mvvm.model.LoginModel;
import com.echo.datatag3.mvvm.view.HomeActivity;
import com.echo.datatag3.mvvm.view.RegisterActivity;
import com.echo.datatag3.util.business.InfoUtil;
import com.echo.datatag3.util.business.UserUtil;
import com.echo.datatag3.util.kt.DialogUtil;
import com.echo.datatag3.util.system.SharedSharedPreferencesUtil;
import com.echo.datatag3.util.system.ToastUtil;

public class LoginViewModel extends BaseViewModel<LoginModel> {
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void forgetPassword(){

    }

    public void login(){
        if (getModel().getAccount()==null||getModel().getAccount().isEmpty()){
            ToastUtil.toast("请填写账号");
        }else if (getModel().getPassword()==null||getModel().getPassword().isEmpty()){
            ToastUtil.toast("请填写密码");
        }else if (!getModel().isConsentPolicy()){
            ToastUtil.toast("请阅读并同意用户协议和隐私协议");
        }
        else {
            if (!getModel().isLogging()){
                getModel().setLogging(true);
                UserUtil.login(new LoginCallback() {
                    @Override
                    public void onSuccess(Long userId, Integer point, String token) {
                        getModel().setLogging(false);
                        ToastUtil.success("登陆成功");
                        InfoUtil.refreshUserId(userId);
                        InfoUtil.refreshToken(token);
                        InfoUtil.refreshPoint(point);
                        InfoUtil.refreshAccount(getModel().getAccount());
                        InfoUtil.refreshPassword(getModel().getPassword());
                        InfoUtil.refreshUserName("");
                        createIntent(HomeActivity.class, true);
                    }

                    @Override
                    public void onAccountNotExist() {
                        ToastUtil.error("账号不存在");
                        getModel().setLogging(false);
                    }

                    @Override
                    public void onPasswordError() {
                        ToastUtil.error("密码错误");
                        getModel().setLogging(false);
                    }

                    @Override
                    public void onRequestError() {
                        ToastUtil.toast("网络连接失败");
                        getModel().setLogging(false);
                    }

                    @Override
                    public void onErrorWithCode(@NonNull String code) {
                        ToastUtil.info("未知错误"+code);
                        getModel().setLogging(false);
                    }

                    @Override
                    public void onIOException(@NonNull String error) {
                        ToastUtil.toast("服务器无应答");
                        getModel().setLogging(false);
                    }
                }, getModel().getAccount(), null, null, getModel().getPassword());
            }
        }
    }

    public void toRegister() {
        createIntent(RegisterActivity.class);
    }

    @SuppressLint("DefaultLocale")
    public void loginSettings() {
        DialogUtil.loginSettings(getModel().getContext(), (ip1, ip2, ip3, ip4, port) -> {
            try {
                int IP1 = Integer.parseInt(ip1);
                int IP2 = Integer.parseInt(ip2);
                int IP3 = Integer.parseInt(ip3);
                int IP4 = Integer.parseInt(ip4);
                int PORT = Integer.parseInt(port);
                if (IP1 < 0 || IP1 > 255 || IP2 < 0 || IP2 > 255 || IP3 < 0 || IP3 > 255 || IP4 < 0 || IP4 > 255 || PORT < 0 || PORT > 65535) {
                    ToastUtil.error("参数错误");
                } else {
                    String ipConfig = String.format("%d.%d.%d.%d:%d", IP1, IP2, IP3, IP4, PORT);
                    SharedSharedPreferencesUtil.addValue("network_address", ipConfig);
//                    BaseRequest.setNetworkAddress(ipConfig);
                    ToastUtil.success("修改成功");
                }
            }catch (NumberFormatException e){
                ToastUtil.error("参数错误");
            }
        });
    }
}
