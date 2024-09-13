package com.echo.dapc.mvvm.view;

import com.echo.dapc.R;
import com.echo.dapc.base.activity.BaseDataBindingActivity;
import com.echo.dapc.bean.logic.User;
import com.echo.dapc.databinding.ActivityUserInfoEditBinding;
import com.echo.dapc.interfaces.callback.user.QueryUserCallback;
import com.echo.dapc.mvvm.model.UserInfoModel;
import com.echo.dapc.mvvm.viewmodel.UserInfoViewModel;
import com.echo.dapc.util.business.UserUtil;
import com.echo.dapc.util.system.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends BaseDataBindingActivity<UserInfoViewModel, UserInfoModel, ActivityUserInfoEditBinding> {
    /**
     * 获取当前Activity绑定的布局文件的ID
     *
     * @return 如R.layout.activity_xxx.xml
     */
    @Override
    protected int getUILayoutId() {
        return R.layout.activity_user_info;
    }

    /**
     * 在主线程的初始化操作，如为一些特殊组件添加监听器
     */
    @Override
    protected void initOnMainThread() {
        long id = getLongExtra("user_id", 0);
        if (id==0)return;
        UserUtil.queryUser(new QueryUserCallback() {
            @Override
            public void onSuccess(User user) {
                getModel().setUser(user);
            }

            @Override
            public void onUserNotExist() {
                ToastUtil.error("加载失败");
            }

            @Override
            public void onError(String error) {
                ToastUtil.error("加载失败");
            }
        }, id);
    }

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    @Override
    protected void initInBackground() {

    }
}