package com.echo.datatag3.mvvm.view.task;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.databinding.PageTaskCompletedInfoBinding;
import com.echo.datatag3.mvvm.model.task.TaskCompletedInfoModel;
import com.echo.datatag3.mvvm.viewmodel.taskinfo.TaskCompletedInfoViewModel;

public class TaskCompletedInfoPage extends BaseCustomActivity<TaskCompletedInfoViewModel, TaskCompletedInfoModel, PageTaskCompletedInfoBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.page_task_completed_info;
    }

    @Override
    protected void init() {

    }
}