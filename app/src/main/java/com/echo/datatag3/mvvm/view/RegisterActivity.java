package com.echo.datatag3.mvvm.view;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.databinding.ActivityRegisterBinding;
import com.echo.datatag3.mvvm.model.RegisterModel;
import com.echo.datatag3.mvvm.viewmodel.RegisterViewModel;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RegisterActivity extends BaseCustomActivity<RegisterViewModel, RegisterModel, ActivityRegisterBinding> {


    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        dispatchFromClasses(new Class[]{MaterialEditText.class});
        getBinding().titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }
        });
    }
}