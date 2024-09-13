package com.echo.dapc.mvvm.view;

import com.echo.dapc.R;
import com.echo.dapc.base.activity.BaseDataBindingActivity;
import com.echo.dapc.bean.entity.FriendRequest;
import com.echo.dapc.bean.enumeration.Gender;
import com.echo.dapc.databinding.ActivityFriendRequestBinding;
import com.echo.dapc.interfaces.callback.user.QueryUserNicknameCallback;
import com.echo.dapc.mvvm.model.FriendRequestModel;
import com.echo.dapc.mvvm.viewmodel.FriendRequestViewModel;
import com.echo.dapc.util.business.AvatarUtil;
import com.echo.dapc.util.business.InfoUtil;
import com.echo.dapc.util.business.UserUtil;
import com.echo.dapc.util.system.JsonUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestActivity extends BaseDataBindingActivity<FriendRequestViewModel, FriendRequestModel, ActivityFriendRequestBinding> {


    /**
     * 获取当前Activity绑定的布局文件的ID
     *
     * @return 如R.layout.activity_xxx.xml
     */
    @Override
    protected int getUILayoutId() {
        return R.layout.activity_friend_request;
    }

    /**
     * 在主线程的初始化操作，如为一些特殊组件添加监听器
     */
    @Override
    protected void initOnMainThread() {
        getBinding().titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }
        });
        getViewModel().getModel().setRequest(JsonUtil.fromJson(getStringExtra("friend_request"), FriendRequest.class));
        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, InfoUtil.getUserId().longValue() == getModel().getRequest().getRequesterId().longValue() ? getModel().getRequest().getUserId() : getModel().getRequest().getRequesterId(), getBinding().userAvatar, false);
        getViewModel().getModel().setMineRequest(InfoUtil.getUserId().longValue()==getModel().getRequest().getRequesterId());

    }

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    @Override
    protected void initInBackground() {
        UserUtil.queryUserNickname(new QueryUserNicknameCallback() {
            @Override
            public void onSuccess(String nickname) {
                getViewModel().getModel().setNickname(nickname);
            }

            @Override
            public void onUserNotExist() {

            }

            @Override
            public void onError(String error) {

            }
        }, InfoUtil.getUserId().longValue() == getModel().getRequest().getRequesterId().longValue() ? getModel().getRequest().getUserId() : getModel().getRequest().getRequesterId());

    }
}