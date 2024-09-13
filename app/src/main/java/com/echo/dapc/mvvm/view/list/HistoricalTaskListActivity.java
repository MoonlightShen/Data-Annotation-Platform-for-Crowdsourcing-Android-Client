package com.echo.dapc.mvvm.view.list;

import androidx.annotation.NonNull;

import com.echo.dapc.adapter.ClickCallback;
import com.echo.dapc.adapter.TaskListAdapter;
import com.echo.dapc.adapter.viewholder.task.TaskViewHolder;
import com.echo.dapc.base.activity.BaseItemListActivity;
import com.echo.dapc.base.adapter.BaseRecycleListAdapter;
import com.echo.dapc.bean.logic.Task;
import com.echo.dapc.interfaces.callback.task.LoadAllTasksCallback;
import com.echo.dapc.util.business.TaskUtil;
import com.echo.dapc.util.system.ToastUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

public class HistoricalTaskListActivity extends BaseItemListActivity<Task, TaskViewHolder> {

    @NonNull
    @Override
    protected String getTitleString() {
        return "历史任务";
    }

    @NonNull
    @Override
    protected BaseRecycleListAdapter<Task, TaskViewHolder> createAdapter() {
        return new TaskListAdapter(getContext(), getModel().getLauncher(), new ClickCallback() {
            @Override
            public void clickPosition(int position) {
                getModel().setLastClickPosition(position);
            }
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

    }

    @NonNull
    @Override
    protected OnRefreshLoadMoreListener createRefreshLoadMoreListener() {
        return new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
                //TODO 加载更多历史任务
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                TaskUtil.loadHistoricalTasks(new LoadAllTasksCallback() {
                    @Override
                    public void onSuccess(List<Task> tasks) {
                        showItems(tasks);
                    }

                    @Override
                    public void onError(Object errorMessage) {
                        ToastUtil.error("刷新失败");
                        showError();
                    }
                });
            }
        };
    }
}