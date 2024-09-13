package com.echo.dapc.mvvm.view;

import com.echo.dapc.R;
import com.echo.dapc.base.activity.BaseDataBindingActivity;
import com.echo.dapc.databinding.ActivitySettingsBinding;
import com.echo.dapc.mvvm.model.SettingsModel;
import com.echo.dapc.mvvm.viewmodel.SettingsViewModel;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

public class SettingsActivity extends BaseDataBindingActivity<SettingsViewModel, SettingsModel, ActivitySettingsBinding> {

    /**
     * 获取当前Activity绑定的布局文件的ID
     *
     * @return 如R.layout.activity_xxx.xml
     */
    @Override
    protected int getUILayoutId() {
        return R.layout.activity_settings;
    }

    /**
     * 在主线程的初始化操作，如为一些特殊组件添加监听器
     */
    @Override
    protected void initOnMainThread() {
        getBinding().listTbTitle.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }
        });
    }

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    @Override
    protected void initInBackground() {

    }
}