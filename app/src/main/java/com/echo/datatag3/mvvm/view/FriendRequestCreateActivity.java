package com.echo.datatag3.mvvm.view;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.bean.logic.User;
import com.echo.datatag3.databinding.ActivityFriendRequestCreateBinding;
import com.echo.datatag3.interfaces.callback.user.PostFriendRequestCallback;
import com.echo.datatag3.interfaces.callback.user.QueryUserCallback;
import com.echo.datatag3.mvvm.model.FriendRequestCreateModel;
import com.echo.datatag3.mvvm.viewmodel.FriendRequestCreateViewModel;
import com.echo.datatag3.util.business.AvatarUtil;
import com.echo.datatag3.util.business.FriendRequestUtil;
import com.echo.datatag3.util.business.UserUtil;
import com.echo.datatag3.util.system.ToastUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestCreateActivity extends BaseCustomActivity<FriendRequestCreateViewModel, FriendRequestCreateModel, ActivityFriendRequestCreateBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_friend_request_create;
    }

    @Override
    protected void init() {
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
                    ToastUtil.toast("请输入验证消息");
                }
            }
        });
    }

    @Override
    protected List<Runnable> loadWithChildThread() {
        List<Runnable> runnableList = new ArrayList<>();
        runnableList.add(() -> {
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
        });
        return runnableList;
    }

    @Override
    protected void loadSuccess() {
        super.loadSuccess();
    }
}