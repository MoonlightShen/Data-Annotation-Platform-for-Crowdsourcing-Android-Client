package com.echo.datatag3.mvvm.view;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.bean.logic.User;
import com.echo.datatag3.databinding.ActivityUserInfoEditBinding;
import com.echo.datatag3.interfaces.callback.user.QueryUserCallback;
import com.echo.datatag3.mvvm.model.UserInfoModel;
import com.echo.datatag3.mvvm.viewmodel.UserInfoViewModel;
import com.echo.datatag3.util.business.UserUtil;
import com.echo.datatag3.util.system.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends BaseCustomActivity<UserInfoViewModel, UserInfoModel, ActivityUserInfoEditBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void init() {

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