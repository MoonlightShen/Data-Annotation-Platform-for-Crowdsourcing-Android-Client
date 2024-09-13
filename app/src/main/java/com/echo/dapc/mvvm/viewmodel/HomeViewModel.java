package com.echo.dapc.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.interfaces.callback.common.CommonCallback;
import com.echo.dapc.mvvm.model.HomeModel;
import com.echo.dapc.mvvm.view.LoginActivity;
import com.echo.dapc.mvvm.view.SettingsActivity;
import com.echo.dapc.mvvm.view.UserInfoEditActivity;
import com.echo.dapc.mvvm.view.list.DataFileManage;
import com.echo.dapc.mvvm.view.list.HistoricalTaskListActivity;
import com.echo.dapc.mvvm.view.list.SystemNoticeListActivity;
import com.echo.dapc.mvvm.view.list.TaskManagePage;
import com.echo.dapc.util.business.InfoUtil;
import com.echo.dapc.util.business.UserUtil;
import com.echo.dapc.util.system.ToastUtil;

public class HomeViewModel extends BaseViewModel<HomeModel> {
    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void editUserInfo(){createIntent(UserInfoEditActivity.class);}
    public void openDataFileManagePage(){createIntent(DataFileManage.class);}
    public void openTaskManagePage(){
        createIntent(TaskManagePage.class);
    }
    public void openHistoricalTaskListPage(){createIntent(HistoricalTaskListActivity.class);}
    public void openSettingsPage(){createIntent(SettingsActivity.class);}
    public void logout(){
        UserUtil.logout(new CommonCallback() {
            @Override
            public void onSuccess() {
                InfoUtil.refreshPassword("");
                ToastUtil.success("退出账号成功");
                createIntent(LoginActivity.class, true);
            }

            @Override
            public void onError(String error) {
                InfoUtil.refreshPassword("");
                ToastUtil.info("未知错误"+error);
                createIntent(LoginActivity.class, true);
            }

            @Override
            public void onIOException() {
                InfoUtil.refreshPassword("");
                ToastUtil.normal("服务器无响应");
                createIntent(LoginActivity.class, true);
            }
        });
    }
    public void openSystemNoticeListPage(){
        createIntent(SystemNoticeListActivity.class);
    }

}
