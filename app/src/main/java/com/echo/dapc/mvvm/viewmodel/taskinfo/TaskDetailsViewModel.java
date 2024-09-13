package com.echo.dapc.mvvm.viewmodel.taskinfo;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.mvvm.model.task.TaskDetailsInfoModel;
import com.echo.dapc.util.business.TaskUtil;

public class TaskDetailsViewModel extends BaseViewModel<TaskDetailsInfoModel> {
    public TaskDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public void likeTask() {
        if (getModel().isHasLike()) {
            TaskUtil.likeTask(() -> {
                getModel().addLike();
                getModel().setHasLike(true);
            }, getModel().getTaskId());
        } else {
            TaskUtil.cancelLikeTask(() -> {
                getModel().reduceLike();
                getModel().setHasLike(false);
            }, getModel().getTaskId());
        }
    }

    public void starTask() {
        if (getModel().isHasStar()) {
            getModel().addStar();
            getModel().setHasStar(true);
        } else {
            getModel().reduceStar();
            getModel().setHasStar(false);
        }
    }
}
