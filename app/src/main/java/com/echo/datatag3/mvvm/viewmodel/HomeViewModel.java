package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.interfaces.callback.common.CommonCallback;
import com.echo.datatag3.mvvm.model.HomeModel;
import com.echo.datatag3.mvvm.view.LoginActivity;
import com.echo.datatag3.mvvm.view.SettingsActivity;
import com.echo.datatag3.mvvm.view.UserInfoEditActivity;
import com.echo.datatag3.mvvm.view.list.DataFileManage;
import com.echo.datatag3.mvvm.view.list.HistoricalTaskListActivity;
import com.echo.datatag3.mvvm.view.list.SystemNoticeListActivity;
import com.echo.datatag3.mvvm.view.list.TaskManagePage;
import com.echo.datatag3.util.business.InfoUtil;
import com.echo.datatag3.util.business.UserUtil;
import com.echo.datatag3.util.system.ToastUtil;

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
                ToastUtil.toast("服务器无响应");
                createIntent(LoginActivity.class, true);
            }
        });
    }
    public void openSystemNoticeListPage(){
        createIntent(SystemNoticeListActivity.class);
    }

}
