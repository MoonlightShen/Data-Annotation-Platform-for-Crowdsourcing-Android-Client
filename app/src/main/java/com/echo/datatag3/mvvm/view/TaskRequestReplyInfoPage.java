package com.echo.datatag3.mvvm.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.databinding.PageTaskRequestReplyInfoBinding;
import com.echo.datatag3.mvvm.model.TaskRequestReplyInfoModel;
import com.echo.datatag3.mvvm.viewmodel.TaskRequestReplyInfoViewModel;

public class TaskRequestReplyInfoPage extends BaseCustomActivity<TaskRequestReplyInfoViewModel, TaskRequestReplyInfoModel, PageTaskRequestReplyInfoBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.page_task_request_reply_info;
    }

    @Override
    protected void init() {

    }
}