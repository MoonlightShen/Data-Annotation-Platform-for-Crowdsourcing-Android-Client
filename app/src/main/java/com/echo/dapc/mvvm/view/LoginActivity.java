package com.echo.dapc.mvvm.view;

import android.widget.TextView;

import com.echo.dapc.R;
import com.echo.dapc.base.activity.BaseDataBindingActivity;
import com.echo.dapc.databinding.ActivityLoginBinding;
import com.echo.dapc.mvvm.model.LoginModel;
import com.echo.dapc.mvvm.viewmodel.LoginViewModel;
import com.echo.dapc.util.business.InfoUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseDataBindingActivity<LoginViewModel, LoginModel, ActivityLoginBinding> {

    /**
     * 获取当前Activity绑定的布局文件的ID
     *
     * @return 如R.layout.activity_xxx.xml
     */
    @Override
    protected int getUILayoutId() {
        return R.layout.activity_login;
    }

    /**
     * 在主线程的初始化操作，如为一些特殊组件添加监听器
     */
    @Override
    protected void initOnMainThread() {
        String account = InfoUtil.getAccount();
        if (account!=null){
            getViewModel().getModel().setAccount(account);
            getViewModel().getModel().setPassword(InfoUtil.getPassword());
            getViewModel().getModel().setConsentPolicy(true);
        }
    }

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    @Override
    protected void initInBackground() {

    }

    /**
     * 监控的组件类型列表，如果这些组件拥有焦点且用户产生了不满足对应监控策略的行为，则清除其焦点并隐藏键盘
     *
     * @return
     */
    @Override
    protected MonitorClass[] clearFocusOfClasses() {
        return new MonitorClass[]{new MonitorClass(MaterialEditText.class, MonitorStrategy.ROW), new MonitorClass(TextView.class, MonitorStrategy.ROW)};
    }
}