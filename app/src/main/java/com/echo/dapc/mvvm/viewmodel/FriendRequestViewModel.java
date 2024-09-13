package com.echo.dapc.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.interfaces.callback.user.HandleFriendRequestCallback;
import com.echo.dapc.mvvm.model.FriendRequestModel;
import com.echo.dapc.util.business.FriendRequestUtil;
import com.echo.dapc.util.system.ToastUtil;

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
