package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.interfaces.callback.register.RegisterCheckCallback;
import com.echo.datatag3.mvvm.model.RegisterModel;
import com.echo.datatag3.mvvm.view.RegisterActivity;
import com.echo.datatag3.util.business.UserUtil;
import com.echo.datatag3.util.system.EncryptionUtil;
import com.echo.datatag3.util.system.ToastUtil;

public class RegisterViewModel extends BaseViewModel<RegisterModel> {
    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    private boolean checkAccount(){
        return getModel().getAccount()!=null&&getModel().getAccount().matches("^[a-zA-Z0-9]{5,20}$");
    }

    private boolean checkPhone(){
        return getModel().getPhone()!=null&&getModel().getPhone().matches("^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$");
    }

    private boolean checkPassword(){
        return getModel().getPassword()!=null&&getModel().getPassword().matches("^[a-zA-Z0-9]{5,20}$");
    }

    private boolean checkVerificationCode(){
        //TODO 验证码
        return true;
    }

    public void sendVerificationCode(){
        if (checkPhone()){
            //TODO 调用API获取验证码
            ToastUtil.toast("随机输入验证码");
        }else {
            ToastUtil.error("手机号格式错误");
        }
    }

    public void register(){
        if (checkAccount()){
            if (checkPassword()){
                if (checkPhone()){
                    if (checkVerificationCode()){
                        UserUtil.register(new RegisterCheckCallback() {
                            @Override
                            public void onAccountExist() {
                                ToastUtil.error("账号已存在");
                            }

                            @Override
                            public void onPhoneExist() {
                                ToastUtil.error("手机号已存在");
                            }

                            @Override
                            public void onEmailExist() {
                                ToastUtil.error("邮箱已存在");
                            }

                            @Override
                            public void onSuccess() {
                                ToastUtil.success("注册成功");
                                getModel().getHandler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((RegisterActivity)getModel().getContext()).finish();
                                    }
                                }, 500);
                            }

                            @Override
                            public void onFailure(String code) {
                                ToastUtil.error("注册失败"+code);
                            }

                            @Override
                            public void onNetworkError(String errorMessage) {

                            }
                        }, getModel().getAccount(), getModel().getPhone(), null, EncryptionUtil.string2md5(getModel().getPassword()));
                    }else{
                        ToastUtil.error("请先获取验证码");
                    }
                }else {
                    ToastUtil.error("请输入有效手机号");
                }
            }else {
                ToastUtil.error("密码应为5-20位的数字和字母组合");
            }
        }else{
            ToastUtil.error("账号应为5-20位的数字和字母组合");
        }
    }
}
