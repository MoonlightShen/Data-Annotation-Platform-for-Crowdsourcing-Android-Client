package com.echo.datatag3.mvvm.view.list;

import androidx.annotation.NonNull;

import com.echo.datatag3.bean.entity.LocalTask;
import com.echo.datatag3.bean.logic.Task;
import com.echo.datatag3.interfaces.callback.common.CommonEntityCallback;
import com.echo.datatag3.interfaces.callback.common.SQLiteDBQueryCallback;
import com.echo.datatag3.interfaces.callback.task.LoadAllLocalTasksCallback;
import com.echo.datatag3.util.business.TaskUtil;
import com.echo.datatag3.util.system.ThreadUtil;
import com.echo.datatag3.util.system.ToastUtil;
import com.hjq.bar.TitleBar;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class TaskManagePage extends BaseTaskListPage {

    @NonNull
    @Override
    protected String getTitleString() {
        return "任务管理";
    }

    @Override
    protected void initLoading() {
        TaskUtil.initLoadTasks(new SQLiteDBQueryCallback<Task>() {
            @Override
            public void onSuccess(List<Task> list) {
                showItems(list);

            }

            @Override
            public void onSQLiteDBError() {
                SQLiteDBQueryCallback.super.onSQLiteDBError();
                showError();
            }
        });
        getBinding().listSrl.setEnableLoadMore(false);
    }

    @NonNull
    @Override
    protected OnRefreshLoadMoreListener createRefreshLoadMoreListener() {
        return new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (newTask) {
                    newTask = false;
                    TaskUtil.loadAllLocalTasks(new LoadAllLocalTasksCallback() {
                        @Override
                        public void onSuccess(List<LocalTask> localTasks) {
                            List<Task> tasks = new ArrayList<>();
                            for (LocalTask localTask : localTasks) {
                                tasks.add(Task.fromLocal(localTask));
                            }
                            for (Task task : getItems()) {
                                if (task.getTaskId() != null && task.getTaskIndex() == null) {
                                    tasks.add(task);
                                }
                            }
                            ThreadUtil.runOnUiThread(() -> {
                                refreshLayout.finishRefresh();
                                showItems(tasks);
                            });
                        }

                        @Override
                        public void onError(Object errorMessage) {
                            showError();
                        }
                    });
                } else if (getLastClickEntity() != null) {
                    Task task = getLastClickEntity();
                    TaskUtil.refreshTask(new CommonEntityCallback<>() {
                        @Override
                        public void onSuccess(Task task) {
                            refreshLayout.finishRefresh();
                            refreshItem(task, getLastClickPosition());
                        }

                        @Override
                        public void onSQLiteDBError() {
                            refreshLayout.finishRefresh();
                            showError();
                            ToastUtil.toast("数据库异常");
                        }

                        @Override
                        public void onIOException() {
                            refreshLayout.finishRefresh();
                            showError();
                            ToastUtil.toast("服务器无响应");
                        }

                        @Override
                        public void unknownError(String errorCode) {
                            refreshLayout.finishRefresh();
                            showError();
                            ToastUtil.info("未知错误" + errorCode);
                        }
                    }, task.getTaskIndex() != null ? task.getTaskIndex() : 0, task.getTaskId() != null ? task.getTaskId() : 0);
                } else {
                    refreshLayout.finishRefresh();
                }
            }
        };
    }

    @Override
    protected void onRightClick(TitleBar titleBar) {

    }

    @Override
    protected boolean buildFloatingButton() {
        return true;
    }

}