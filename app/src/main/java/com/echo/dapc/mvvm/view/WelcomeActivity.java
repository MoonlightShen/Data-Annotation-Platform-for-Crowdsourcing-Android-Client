package com.echo.dapc.mvvm.view;

import androidx.annotation.NonNull;

import com.echo.dapc.R;
import com.echo.dapc.base.activity.BaseDataBindingActivity;
import com.echo.dapc.databinding.ActivityWelcomeBinding;
import com.echo.dapc.interfaces.callback.user.LoginCallback;
import com.echo.dapc.mvvm.model.DefaultModel;
import com.echo.dapc.mvvm.viewmodel.DefaultViewModel;
import com.echo.dapc.util.business.InfoUtil;
import com.echo.dapc.util.business.UserUtil;
import com.echo.dapc.util.system.ToastUtil;

public class WelcomeActivity extends BaseDataBindingActivity<DefaultViewModel, DefaultModel, ActivityWelcomeBinding> {

    /**
     * 获取当前Activity绑定的布局文件的ID
     *
     * @return 如R.layout.activity_xxx.xml
     */
    @Override
    protected int getUILayoutId() {
        return R.layout.activity_welcome;
    }

    /**
     * 在主线程的初始化操作，如为一些特殊组件添加监听器
     */
    @Override
    protected void initOnMainThread() {
        String account = InfoUtil.getAccount();
        String password = InfoUtil.getPassword();
        if (account != null && !account.isEmpty() && password != null && !password.isEmpty()) {
            UserUtil.login(new LoginCallback() {
                @Override
                public void onSuccess(Long userId, Integer point, String token) {
                    InfoUtil.refreshUserId(userId);
                    InfoUtil.refreshPoint(point);
                    InfoUtil.refreshToken(token);
                    createIntent(HomeActivity.class, true);
//                    createIntent(TestPage.class, true);
//                    createIntent(LabelClassificationTaggingPage.class, true);
                }

                @Override
                public void onAccountNotExist() {
                    InfoUtil.refreshAccount("");
                    InfoUtil.refreshPassword("");
                    createIntent(LoginActivity.class, true);
                }

                @Override
                public void onPasswordError() {
                    ToastUtil.error("密码错误");
                    InfoUtil.refreshPassword("");
                    createIntent(LoginActivity.class, true);
                }

                @Override
                public void onRequestError() {
                    ToastUtil.normal("请检查网络连接");
                    InfoUtil.refreshPassword("");
                    createIntent(LoginActivity.class, true);
                }

                @Override
                public void onErrorWithCode(@NonNull String code) {
                    ToastUtil.info("未知错误" + code);
                    createIntent(LoginActivity.class, true);
                }

                @Override
                public void onIOException(@NonNull String error) {
                    ToastUtil.normal("服务器未响应");
                    InfoUtil.refreshPassword("");
                    postDelayed(()->createIntent(LoginActivity.class, true), 500);
                }
            }, account, null, null, password);
        }else {
            createIntent(LoginActivity.class, true);
        }
    }

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    @Override
    protected void initInBackground() {

    }

}