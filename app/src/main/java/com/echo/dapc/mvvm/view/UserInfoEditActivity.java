package com.echo.dapc.mvvm.view;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.bumptech.glide.Glide;
import com.echo.dapc.R;
import com.echo.dapc.base.activity.BaseDataBindingActivity;
import com.echo.dapc.bean.enumeration.Gender;
import com.echo.dapc.bean.logic.User;
import com.echo.dapc.databinding.ActivityUserInfoEditBinding;
import com.echo.dapc.interfaces.callback.user.QueryUserCallback;
import com.echo.dapc.interfaces.callback.user.UpdateUserAvatarCallback;
import com.echo.dapc.mvvm.model.UserInfoEditModel;
import com.echo.dapc.mvvm.viewmodel.UserInfoEditViewModel;
import com.echo.dapc.util.business.AvatarUtil;
import com.echo.dapc.util.business.InfoUtil;
import com.echo.dapc.util.business.UserUtil;
import com.echo.dapc.util.system.ToastUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.utils.MediaUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

public class UserInfoEditActivity extends BaseDataBindingActivity<UserInfoEditViewModel, UserInfoEditModel, ActivityUserInfoEditBinding> {

    /**
     * 获取当前Activity绑定的布局文件的ID
     *
     * @return 如R.layout.activity_xxx.xml
     */
    @Override
    protected int getUILayoutId() {
        return R.layout.activity_user_info_edit;
    }

    /**
     * 在主线程的初始化操作，如为一些特殊组件添加监听器
     */
    @Override
    protected void initOnMainThread() {
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

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    @Override
    protected void initInBackground() {
        UserUtil.queryUser(new QueryUserCallback() {
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
        }, InfoUtil.getUserId());
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

    /**
     * 监控的组件类型列表，如果这些组件拥有焦点且用户产生了不满足对应监控策略的行为，则清除其焦点并隐藏键盘
     *
     * @return
     */
    @Override
    protected MonitorClass[] clearFocusOfClasses() {
        return new MonitorClass[]{new MonitorClass(MaterialEditText.class, MonitorStrategy.ROW)};
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