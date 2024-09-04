package com.echo.datatag3.mvvm.view;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.databinding.PageLoadingErrorBinding;
import com.echo.datatag3.mvvm.model.DefaultModel;
import com.echo.datatag3.mvvm.viewmodel.DefaultViewModel;
import com.echo.datatag3.util.system.ToastUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

public class LoadingErrorPage extends BaseCustomActivity<DefaultViewModel, DefaultModel, PageLoadingErrorBinding> {


    @Override
    protected int getContentViewId() {
        return R.layout.page_loading_error;
    }

    @Override
    protected void init() {
        getBinding().titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }

            @Override
            public void onTitleClick(TitleBar titleBar) {
                ToastUtil.error("出错啦，请返回上一页面重试");
            }
        });
    }
}