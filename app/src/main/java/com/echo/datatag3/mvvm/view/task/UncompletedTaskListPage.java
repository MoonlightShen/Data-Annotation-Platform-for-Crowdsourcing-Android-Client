package com.echo.datatag3.mvvm.view.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.echo.datatag3.R;
import com.echo.datatag3.adapter.ClickCallback;
import com.echo.datatag3.adapter.TaskListAdapter;
import com.echo.datatag3.adapter.viewholder.task.TaskViewHolder;
import com.echo.datatag3.base.BaseItemListActivity;
import com.echo.datatag3.base.BaseItemListAdapter;
import com.echo.datatag3.base.BaseRecycleViewAdapter;
import com.echo.datatag3.bean.logic.Task;
import com.echo.datatag3.interfaces.callback.common.CommonEntityListCallback;
import com.echo.datatag3.interfaces.callback.task.LoadAllTasksCallback;
import com.echo.datatag3.mvvm.view.LoadingErrorPage;
import com.echo.datatag3.util.business.InfoUtil;
import com.echo.datatag3.util.business.TaskUtil;
import com.echo.datatag3.util.system.JsonUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

public class UncompletedTaskListPage extends BaseItemListActivity<Task, TaskViewHolder> {

    @NonNull
    @Override
    protected String getTitleString() {
        return "未完成任务";
    }

    @NonNull
    @Override
    protected BaseItemListAdapter<Task, TaskViewHolder> createAdapter() {
        return new TaskListAdapter(getContext(), getModel().getLauncher(), position -> {
            getModel().setLastClickPosition(position);
        });
    }

    @NonNull
    @Override
    protected OnTitleBarListener getTitleBarListener() {
        return new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }
        };
    }

    @Override
    protected void initLoading() {
        TaskUtil.queryUncompletedTask(this::showItems);
    }

    @NonNull
    @Override
    protected OnRefreshLoadMoreListener createRefreshLoadMoreListener() {
        return new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        };
    }
}