package com.echo.datatag3.mvvm.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.databinding.PageHandleTaskRequestBinding;
import com.echo.datatag3.mvvm.model.HandleTaskRequestModel;
import com.echo.datatag3.mvvm.viewmodel.HandleTaskRequestViewModel;

public class HandleTaskRequestPage extends BaseCustomActivity<HandleTaskRequestViewModel, HandleTaskRequestModel, PageHandleTaskRequestBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.page_handle_task_request;
    }

    @Override
    protected void init() {

    }
}