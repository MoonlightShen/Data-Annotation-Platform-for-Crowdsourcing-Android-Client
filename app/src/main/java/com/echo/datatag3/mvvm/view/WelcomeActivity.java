package com.echo.datatag3.mvvm.view;

import androidx.annotation.NonNull;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.databinding.ActivityWelcomeBinding;
import com.echo.datatag3.interfaces.callback.user.LoginCallback;
import com.echo.datatag3.mvvm.model.DefaultModel;
import com.echo.datatag3.mvvm.viewmodel.DefaultViewModel;
import com.echo.datatag3.util.business.InfoUtil;
import com.echo.datatag3.util.business.UserUtil;
import com.echo.datatag3.util.system.ToastUtil;

public class WelcomeActivity extends BaseCustomActivity<DefaultViewModel, DefaultModel, ActivityWelcomeBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void init() {
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
                    ToastUtil.toast("请检查网络连接");
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
                    ToastUtil.toast("服务器未响应");
                    InfoUtil.refreshPassword("");
                    postDelayed(()->createIntent(LoginActivity.class, true), 500);
                }
            }, account, null, null, password);
        }else {
            createIntent(LoginActivity.class, true);
        }
    }

}