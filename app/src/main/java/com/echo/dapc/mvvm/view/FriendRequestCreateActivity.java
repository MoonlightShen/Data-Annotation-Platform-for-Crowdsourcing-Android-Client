package com.echo.dapc.mvvm.view;

import com.echo.dapc.R;
import com.echo.dapc.base.activity.BaseDataBindingActivity;
import com.echo.dapc.bean.logic.User;
import com.echo.dapc.databinding.ActivityFriendRequestCreateBinding;
import com.echo.dapc.interfaces.callback.user.PostFriendRequestCallback;
import com.echo.dapc.interfaces.callback.user.QueryUserCallback;
import com.echo.dapc.mvvm.model.FriendRequestCreateModel;
import com.echo.dapc.mvvm.viewmodel.FriendRequestCreateViewModel;
import com.echo.dapc.util.business.AvatarUtil;
import com.echo.dapc.util.business.FriendRequestUtil;
import com.echo.dapc.util.business.UserUtil;
import com.echo.dapc.util.system.ToastUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestCreateActivity extends BaseDataBindingActivity<FriendRequestCreateViewModel, FriendRequestCreateModel, ActivityFriendRequestCreateBinding> {

    /**
     * 获取当前Activity绑定的布局文件的ID
     *
     * @return 如R.layout.activity_xxx.xml
     */
    @Override
    protected int getUILayoutId() {
        return R.layout.activity_friend_request_create;
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
            @Override
            public void onRightClick(TitleBar titleBar) {
                if (getModel().getValidationContent()!=null&&!getModel().getValidationContent().isEmpty()){
                    FriendRequestUtil.postFriendRequest(new PostFriendRequestCallback() {
                        @Override
                        public void onSuccess() {
                            ToastUtil.success("发送成功");
                            getModel().getHandler().postDelayed(FriendRequestCreateActivity.this::finish,500);
                        }

                        @Override
                        public void onError(String code) {

                        }
                    }, getModel().getUser().getUserId(), getModel().getValidationContent());
                }else {
                    ToastUtil.normal("请输入验证消息");
                }
            }
        });
    }

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    @Override
    protected void initInBackground() {
        long id = getLongExtra("user_id", 0);
        if (id==0)return;
        UserUtil.queryUser(new QueryUserCallback() {
            @Override
            public void onSuccess(User user) {
                getModel().setUser(user);
                runOnUiThread(() -> AvatarUtil.loadUserAvatar(user.getGender(), user.getUserId(), getBinding().userAvatar, true));
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
}