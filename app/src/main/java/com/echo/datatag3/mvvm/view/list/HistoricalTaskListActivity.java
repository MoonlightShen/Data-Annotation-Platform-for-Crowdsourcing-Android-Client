package com.echo.datatag3.mvvm.view.list;

import androidx.annotation.NonNull;

import com.echo.datatag3.adapter.ClickCallback;
import com.echo.datatag3.adapter.TaskListAdapter;
import com.echo.datatag3.adapter.viewholder.task.TaskViewHolder;
import com.echo.datatag3.base.BaseItemListActivity;
import com.echo.datatag3.base.BaseItemListAdapter;
import com.echo.datatag3.bean.logic.Task;
import com.echo.datatag3.interfaces.callback.task.LoadAllTasksCallback;
import com.echo.datatag3.util.business.TaskUtil;
import com.echo.datatag3.util.system.ToastUtil;
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
    protected BaseItemListAdapter<Task, TaskViewHolder> createAdapter() {
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