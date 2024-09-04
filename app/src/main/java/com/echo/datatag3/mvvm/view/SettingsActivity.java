package com.echo.datatag3.mvvm.view;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.databinding.ActivitySettingsBinding;
import com.echo.datatag3.mvvm.model.SettingsModel;
import com.echo.datatag3.mvvm.viewmodel.SettingsViewModel;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

public class SettingsActivity extends BaseCustomActivity<SettingsViewModel, SettingsModel, ActivitySettingsBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void init() {
        getBinding().listTbTitle.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }
        });
    }
}