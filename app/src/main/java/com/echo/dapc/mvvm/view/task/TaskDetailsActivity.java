package com.echo.dapc.mvvm.view.task;

import com.echo.dapc.R;
import com.echo.dapc.base.activity.BaseDataBindingActivity;
import com.echo.dapc.bean.entity.LocalTask;
import com.echo.dapc.bean.logic.Task;
import com.echo.dapc.databinding.ActivityTaskDetailsBinding;
import com.echo.dapc.interfaces.callback.user.QueryUserNicknameCallback;
import com.echo.dapc.mvvm.model.task.TaskDetailsInfoModel;
import com.echo.dapc.mvvm.viewmodel.taskinfo.TaskDetailsViewModel;
import com.echo.dapc.util.business.AvatarUtil;
import com.echo.dapc.util.business.TaskUtil;
import com.echo.dapc.util.business.UserUtil;
import com.echo.dapc.util.system.JsonUtil;
import com.echo.dapc.util.system.TimeUtil;
import com.echo.dapc.util.system.ToastUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

public class TaskDetailsActivity extends BaseDataBindingActivity<TaskDetailsViewModel, TaskDetailsInfoModel, ActivityTaskDetailsBinding> {

    /**
     * 获取当前Activity绑定的布局文件的ID
     *
     * @return 如R.layout.activity_xxx.xml
     */
    @Override
    protected int getUILayoutId() {
        return R.layout.activity_task_details;
    }

    /**
     * 在主线程的初始化操作，如为一些特殊组件添加监听器
     */
    @Override
    protected void initOnMainThread() {
        getBinding().taskDetailsTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }
        });
    }

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    @Override
    protected void initInBackground() {
        if (getIntExtra("full", 0) == 1){
            Task task = Task.fromLocal(JsonUtil.fromJson(getStringExtra("full_content"), LocalTask.class));
            getModel().setTitle(task.getTitle());
            getModel().setDescription(task.getDescription());
            if(task.getEndTime()!=null){
                getModel().setRemainingTime(task.getEndTime()- TimeUtil.getCurrentTime());
            }
            getModel().setRemainingQuota(task.getRemainingQuota());
            getModel().setGroupSize(task.getGroupSize());
            getModel().setUnitPoint(task.getUnitPoint());
            getModel().setLikeNum(task.getLikeNum());
            getModel().setStarNum(task.getStarNum());
            getModel().setReadNum(task.getReadNum());
            getModel().setOwnerId(task.getOwnerId());
            runOnUiThread(()-> {
                UserUtil.queryUserNickname(new QueryUserNicknameCallback() {
                    @Override
                    public void onSuccess(String nickname) {
                        getModel().setOwnerNickname(nickname);
                    }

                    @Override
                    public void onUserNotExist() {
                        ToastUtil.error("用户不存在");
                    }

                    @Override
                    public void onError(String error) {
                        ToastUtil.info("未知错误");
                    }
                }, task.getOwnerId());
                AvatarUtil.loadUserAvatar(task.getOwnerId(), getBinding().taskDetailsOwnerAvatar, false);
            });
            return;
        }

        long taskId = getLongExtra("task_id",0);
        if (taskId!=0){
            getModel().setTaskId(taskId);
            TaskUtil.queryTask(task -> {
                getModel().setTitle(task.getTitle());
                getModel().setDescription(task.getDescription());
                if(task.getEndTime()!=null){
                    getModel().setRemainingTime(task.getEndTime()- TimeUtil.getCurrentTime());
                }
                getModel().setRemainingQuota(task.getRemainingQuota());
                getModel().setGroupSize(task.getGroupSize());
                getModel().setUnitPoint(task.getUnitPoint());
                getModel().setLikeNum(task.getLikeNum());
                getModel().setStarNum(task.getStarNum());
                getModel().setReadNum(task.getReadNum());
                getModel().setOwnerId(task.getOwnerId());
                runOnUiThread(()-> {
                    UserUtil.queryUserNickname(new QueryUserNicknameCallback() {
                        @Override
                        public void onSuccess(String nickname) {
                            getModel().setOwnerNickname(nickname);
                        }

                        @Override
                        public void onUserNotExist() {
                            ToastUtil.error("用户不存在");
                        }

                        @Override
                        public void onError(String error) {
                            ToastUtil.info("未知错误");
                        }
                    }, task.getOwnerId());
                    AvatarUtil.loadUserAvatar(task.getOwnerId(), getBinding().taskDetailsOwnerAvatar, false);
                });
                TaskUtil.checkLikeTask(flag -> getModel().setHasLike(flag), taskId);
            }, taskId);
        }else {
            //TODO 异常情况
        }
    }
}