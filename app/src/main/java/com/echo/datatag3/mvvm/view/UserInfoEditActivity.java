package com.echo.datatag3.mvvm.view;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.bumptech.glide.Glide;
import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.bean.enumeration.Gender;
import com.echo.datatag3.bean.logic.User;
import com.echo.datatag3.databinding.ActivityUserInfoEditBinding;
import com.echo.datatag3.interfaces.callback.user.QueryUserCallback;
import com.echo.datatag3.interfaces.callback.user.UpdateUserAvatarCallback;
import com.echo.datatag3.mvvm.model.UserInfoEditModel;
import com.echo.datatag3.mvvm.viewmodel.UserInfoEditViewModel;
import com.echo.datatag3.util.business.AvatarUtil;
import com.echo.datatag3.util.business.InfoUtil;
import com.echo.datatag3.util.business.UserUtil;
import com.echo.datatag3.util.system.ToastUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.utils.MediaUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

public class UserInfoEditActivity extends BaseCustomActivity<UserInfoEditViewModel, UserInfoEditModel, ActivityUserInfoEditBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_info_edit;
    }

    @Override
    protected void init() {
        dispatchFromClasses(new Class[]{MaterialEditText.class});
        getBinding().listTbTitle.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }

            @Override
            public void onRightClick(TitleBar titleBar) {
                getViewModel().saveUserInfo();
            }
        });
        getModel().setAvatar(getBinding().userAvatar);
        AvatarUtil.loadUserAvatar(InfoUtil.getGender(), InfoUtil.getUserId(), getBinding().userAvatar, true);
    }

    @Override
    protected List<Runnable> loadWithChildThread() {
        List<Runnable> runnableList = new ArrayList<>();
        runnableList.add(() -> UserUtil.queryUser(new QueryUserCallback() {
            @Override
            public void onSuccess(User user) {
                getViewModel().getModel().setNickname(user.getNickname());
                getViewModel().getModel().setAge(user.getAge());
                getViewModel().getModel().setPhone(user.getPhone());
                getViewModel().getModel().setEmail(user.getEmail());
                getViewModel().getModel().setIntroduction(user.getIntroduction());
                getViewModel().getModel().setGender(user.getGender());
            }

            @Override
            public void onUserNotExist() {
                ToastUtil.error("加载失败");
            }

            @Override
            public void onError(String error) {
                ToastUtil.info("未知错误" + error);
            }
        }, InfoUtil.getUserId()));
        return runnableList;
    }


    @Override
    protected void loadSuccess() {
        getBinding().genderSpinner.setSelection(getViewModel().getModel().getGender().getIndex());
        getBinding().genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getViewModel().getModel().setGender(Gender.getByIndex(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void handleRes(int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            ArrayList<LocalMedia> result = PictureSelector.obtainSelectorList(data);
            if (result!=null&&!result.isEmpty()){
                LocalMedia media = result.get(0);
                MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(getContext(), media.getPath());
                media.setWidth(imageExtraInfo.getWidth());
                media.setHeight(imageExtraInfo.getHeight());
//                Log.i(TAG, "文件名: " + media.getFileName());
//                Log.i(TAG, "是否压缩:" + media.isCompressed());
//                Log.i(TAG, "压缩:" + media.getCompressPath());
//                Log.i(TAG, "初始路径:" + media.getPath());
//                Log.i(TAG, "绝对路径:" + media.getRealPath());
//                Log.i(TAG, "是否裁剪:" + media.isCut());
//                Log.i(TAG, "裁剪路径:" + media.getCutPath());
//                Log.i(TAG, "是否开启原图:" + media.isOriginal());
//                Log.i(TAG, "原图路径:" + media.getOriginalPath());
//                Log.i(TAG, "沙盒路径:" + media.getSandboxPath());
//                Log.i(TAG, "水印路径:" + media.getWatermarkPath());
//                Log.i(TAG, "视频缩略图:" + media.getVideoThumbnailPath());
//                Log.i(TAG, "原始宽高: " + media.getWidth() + "x" + media.getHeight());
//                Log.i(TAG, "裁剪宽高: " + media.getCropImageWidth() + "x" + media.getCropImageHeight());
//                Log.i(TAG, "文件大小: " + PictureFileUtils.formatAccurateUnitFileSize(media.getSize()));
//                Log.i(TAG, "文件时长: " + media.getDuration());
                runOnUiThread(() -> {
                    UserUtil.updateUserAvatar(new UpdateUserAvatarCallback() {
                        @Override
                        public void onSuccess() {
                            runOnUiThread(() -> {
                                ToastUtil.success("上传成功");
                                Glide.with(getContext())
                                        .load(media.getRealPath())
                                        .into(getBinding().userAvatar);
                            });
                        }

                        @Override
                        public void onTransmissionError() {
                            ToastUtil.error("上传失败，请稍后重试");
                        }

                        @Override
                        public void onError(String error) {
                            ToastUtil.info("未知错误"+error);
                        }

                        @Override
                        public void onIOException(String error) {
                            ToastUtil.info("服务器响应失败");
                        }
                    }, media.getRealPath());
                });
            }
        }
    }
}