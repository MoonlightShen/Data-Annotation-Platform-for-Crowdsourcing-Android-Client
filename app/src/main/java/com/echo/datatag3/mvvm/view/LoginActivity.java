package com.echo.datatag3.mvvm.view;

import android.widget.TextView;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.databinding.ActivityLoginBinding;
import com.echo.datatag3.mvvm.model.LoginModel;
import com.echo.datatag3.mvvm.viewmodel.LoginViewModel;
import com.echo.datatag3.util.business.InfoUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseCustomActivity<LoginViewModel, LoginModel, ActivityLoginBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        dispatchFromClasses(new Class[]{MaterialEditText.class, TextView.class});
    }

    @Override
    protected List<Runnable> loadWithChildThread() {
        List<Runnable> runnableList = new ArrayList<>();
        runnableList.add(() -> {
            String account = InfoUtil.getAccount();
            if (account!=null){
                getViewModel().getModel().setAccount(account);
                getViewModel().getModel().setPassword(InfoUtil.getPassword());
                getViewModel().getModel().setConsentPolicy(true);
            }
        });
        return runnableList;
    }

    @Override
    protected void loadSuccess() {

    }
}