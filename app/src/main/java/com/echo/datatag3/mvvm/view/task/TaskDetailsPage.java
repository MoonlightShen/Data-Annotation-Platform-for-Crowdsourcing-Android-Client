package com.echo.datatag3.mvvm.view.task;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.bean.entity.LocalTask;
import com.echo.datatag3.bean.enumeration.TaskTaggingSceneType;
import com.echo.datatag3.bean.enumeration.TaskVisibleLevel;
import com.echo.datatag3.bean.logic.DataFile;
import com.echo.datatag3.bean.logic.Task;
import com.echo.datatag3.bean.logic.taggingscene.LabelClassification;
import com.echo.datatag3.bean.ui.FlexBoxItem;
import com.echo.datatag3.databinding.PageTaskDetailsBinding;
import com.echo.datatag3.interfaces.callback.common.CommonBooleanCallback;
import com.echo.datatag3.interfaces.callback.common.CommonEntityCallback;
import com.echo.datatag3.interfaces.callback.user.QueryUserNicknameCallback;
import com.echo.datatag3.mvvm.model.task.TaskDetailsInfoModel;
import com.echo.datatag3.mvvm.viewmodel.taskinfo.TaskDetailsViewModel;
import com.echo.datatag3.util.business.AvatarUtil;
import com.echo.datatag3.util.business.TaskUtil;
import com.echo.datatag3.util.business.UserUtil;
import com.echo.datatag3.util.system.JsonUtil;
import com.echo.datatag3.util.system.ThreadUtil;
import com.echo.datatag3.util.system.TimeUtil;
import com.echo.datatag3.util.system.ToastUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class TaskDetailsPage extends BaseCustomActivity<TaskDetailsViewModel, TaskDetailsInfoModel, PageTaskDetailsBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.page_task_details;
    }

    @Override
    protected void init() {
        getBinding().taskDetailsTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }
        });
    }

    @Override
    protected List<Runnable> loadWithChildThread() {
        List<Runnable> runnableList = new ArrayList<>();
        runnableList.add(()->{
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
        });
        return runnableList;
    }

    @Override
    protected void loadSuccess() {
        super.loadSuccess();
    }
}