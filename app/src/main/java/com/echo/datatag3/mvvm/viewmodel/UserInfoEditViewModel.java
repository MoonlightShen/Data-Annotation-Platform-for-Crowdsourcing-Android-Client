package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.interfaces.callback.user.UpdateUserInfoCallback;
import com.echo.datatag3.mvvm.model.UserInfoEditModel;
import com.echo.datatag3.util.business.InfoUtil;
import com.echo.datatag3.util.business.UserUtil;
import com.echo.datatag3.util.system.FileUtil;
import com.echo.datatag3.util.system.ToastUtil;

public class UserInfoEditViewModel extends BaseViewModel<UserInfoEditModel> {
    public UserInfoEditViewModel(@NonNull Application application) {
        super(application);
    }

    public void saveUserInfo(){
        if (getModel().getNickname()==null||getModel().getNickname().isEmpty()){
            ToastUtil.error("昵称不能为空");
            return;
        }
        UserUtil.updateUserInfo(new UpdateUserInfoCallback() {
            @Override
            public void onSuccess() {
                ToastUtil.success("保存成功");
                InfoUtil.refreshUserName(getModel().getNickname());
            }

            @Override
            public void onError(String error) {

            }
        }, getModel().getNickname(), getModel().getGender(), getModel().getAge()==null?-1:getModel().getAge(), getModel().getIntroduction());
    }

    public void uploadAvatar(){
        FileUtil.selectAvatar(getModel().getContext(), getModel().getLauncher());
//        FileUtil.selectAvatar(getModel().getContext(), new OnResultCallbackListener<>() {
//            @Override
//            public void onResult(ArrayList<LocalMedia> result) {
//                Glide.with(getModel().getContext())
//                        .load(result.get(0).getPath())
//                        .into(getModel().getAvatar());
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });
    }
}
