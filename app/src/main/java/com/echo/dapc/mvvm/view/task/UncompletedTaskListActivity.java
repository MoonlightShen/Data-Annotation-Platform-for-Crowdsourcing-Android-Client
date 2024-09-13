package com.echo.dapc.mvvm.view.task;

import androidx.annotation.NonNull;

import com.echo.dapc.adapter.TaskListAdapter;
import com.echo.dapc.adapter.viewholder.task.TaskViewHolder;
import com.echo.dapc.base.activity.BaseItemListActivity;
import com.echo.dapc.base.adapter.BaseRecycleListAdapter;
import com.echo.dapc.bean.logic.Task;
import com.echo.dapc.util.business.TaskUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

public class UncompletedTaskListActivity extends BaseItemListActivity<Task, TaskViewHolder> {

    @NonNull
    @Override
    protected String getTitleString() {
        return "未完成任务";
    }

    @NonNull
    @Override
    protected BaseRecycleListAdapter<Task, TaskViewHolder> createAdapter() {
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