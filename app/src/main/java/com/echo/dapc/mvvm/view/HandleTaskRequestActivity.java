package com.echo.dapc.mvvm.view;

import com.echo.dapc.R;
import com.echo.dapc.base.activity.BaseDataBindingActivity;
import com.echo.dapc.databinding.ActivityHandleTaskRequestBinding;
import com.echo.dapc.mvvm.model.HandleTaskRequestModel;
import com.echo.dapc.mvvm.viewmodel.HandleTaskRequestViewModel;

public class HandleTaskRequestActivity extends BaseDataBindingActivity<HandleTaskRequestViewModel, HandleTaskRequestModel, ActivityHandleTaskRequestBinding> {

    /**
     * 获取当前Activity绑定的布局文件的ID
     *
     * @return 如R.layout.activity_xxx.xml
     */
    @Override
    protected int getUILayoutId() {
        return R.layout.activity_handle_task_request;
    }

    /**
     * 在主线程的初始化操作，如为一些特殊组件添加监听器
     */
    @Override
    protected void initOnMainThread() {

    }

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    @Override
    protected void initInBackground() {

    }
}