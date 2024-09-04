package com.echo.datatag3.mvvm.view;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.bean.entity.FriendRequest;
import com.echo.datatag3.bean.enumeration.Gender;
import com.echo.datatag3.databinding.ActivityFriendRequestBinding;
import com.echo.datatag3.interfaces.callback.user.QueryUserNicknameCallback;
import com.echo.datatag3.mvvm.model.FriendRequestModel;
import com.echo.datatag3.mvvm.viewmodel.FriendRequestViewModel;
import com.echo.datatag3.util.business.AvatarUtil;
import com.echo.datatag3.util.business.InfoUtil;
import com.echo.datatag3.util.business.UserUtil;
import com.echo.datatag3.util.system.JsonUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestActivity extends BaseCustomActivity<FriendRequestViewModel, FriendRequestModel, ActivityFriendRequestBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_friend_request;
    }

    @Override
    protected void init() {
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

    @Override
    protected List<Runnable> loadWithChildThread() {
        List<Runnable> list = new ArrayList<>();
        list.add(() -> {
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
        });
        return list;
    }

    @Override
    protected void loadSuccess() {
        super.loadSuccess();
    }
}