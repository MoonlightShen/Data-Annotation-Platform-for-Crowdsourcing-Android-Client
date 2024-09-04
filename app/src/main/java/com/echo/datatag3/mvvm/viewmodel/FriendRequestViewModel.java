package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.interfaces.callback.user.HandleFriendRequestCallback;
import com.echo.datatag3.mvvm.model.FriendRequestModel;
import com.echo.datatag3.util.business.FriendRequestUtil;
import com.echo.datatag3.util.system.ToastUtil;

public class FriendRequestViewModel extends BaseViewModel<FriendRequestModel> {
    public FriendRequestViewModel(@NonNull Application application) {
        super(application);
    }

    public void refuse(){
        FriendRequestUtil.handleFriendRequest(new HandleFriendRequestCallback() {
            @Override
            public void onAgree() {
            }

            @Override
            public void onIgnore() {
            }

            @Override
            public void onRefuse() {
                ToastUtil.success("操作成功");
            }

            @Override
            public void onError(String code) {
                ToastUtil.info("未知错误"+code);
            }
        }, getModel().getRequest().getRequestId(), getModel().getReply(), null);
    }

    public void agree(){
        FriendRequestUtil.handleFriendRequest(new HandleFriendRequestCallback() {
            @Override
            public void onAgree() {
                ToastUtil.success("操作成功");
            }

            @Override
            public void onIgnore() {
            }

            @Override
            public void onRefuse() {
            }

            @Override
            public void onError(String code) {
                ToastUtil.info("未知错误"+code);
            }
        }, getModel().getRequest().getRequestId(), null, "");
    }

    public void ignore(){
        FriendRequestUtil.handleFriendRequest(new HandleFriendRequestCallback() {
            @Override
            public void onAgree() {
            }

            @Override
            public void onIgnore() {
                ToastUtil.success("操作成功");
            }

            @Override
            public void onRefuse() {
            }

            @Override
            public void onError(String code) {
                ToastUtil.info("未知错误"+code);
            }
        }, getModel().getRequest().getRequestId(), null, null);
    }

}
