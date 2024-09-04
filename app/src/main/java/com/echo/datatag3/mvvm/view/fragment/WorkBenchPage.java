package com.echo.datatag3.mvvm.view.fragment;

import androidx.annotation.NonNull;

import com.echo.datatag3.R;
import com.echo.datatag3.adapter.TaskListAdapter;
import com.echo.datatag3.base.BaseFragment;
import com.echo.datatag3.bean.logic.Task;
import com.echo.datatag3.databinding.PageWorkbenchBinding;
import com.echo.datatag3.interfaces.callback.task.TaskListCallback;
import com.echo.datatag3.mvvm.model.WorkBenchModel;
import com.echo.datatag3.mvvm.view.task.UncompletedTaskListPage;
import com.echo.datatag3.mvvm.viewmodel.WorkBenchViewModel;
import com.echo.datatag3.util.business.TaskUtil;
import com.echo.datatag3.util.system.ToastUtil;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;


public class WorkBenchPage extends BaseFragment<WorkBenchViewModel, WorkBenchModel, PageWorkbenchBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.page_workbench;
    }

    @Override
    protected void init() {
        getBinding().smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
                TaskUtil.getRecommendationTasks(new TaskListCallback() {
                    @Override
                    public void onSuccess(List<Task> tasks, boolean hasMore) {
                        runOnUiThread(() -> getModel().getTaskAdapter().refreshItems(tasks));
                    }

                    @Override
                    public void unknownError(String errorCode) {
                        ToastUtil.info("拉取推荐任务失败"+errorCode);
                        getBinding().smartRefreshLayout.setEnableLoadMore(false);
                        getBinding().recommendationsStatusView.showError();
                    }

                    @Override
                    public void onIOException() {
                        ToastUtil.toast("服务器无响应");
                        getBinding().smartRefreshLayout.setEnableLoadMore(false);
                        getBinding().recommendationsStatusView.showNoNetwork();
                    }

                    @Override
                    public void onSQLiteError() {
                    }
                }, getModel().getCount());
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                TaskUtil.getRecommendationTasks(new TaskListCallback() {
                    @Override
                    public void onSuccess(List<Task> tasks, boolean hasMore) {
                        runOnUiThread(() -> getModel().getTaskAdapter().refreshItems(tasks));
                    }

                    @Override
                    public void unknownError(String errorCode) {
                        ToastUtil.info("拉取推荐任务失败"+errorCode);
                        getBinding().smartRefreshLayout.setEnableLoadMore(false);
                        getBinding().recommendationsStatusView.showError();
                    }

                    @Override
                    public void onIOException() {
                        ToastUtil.toast("服务器无响应");
                        getBinding().smartRefreshLayout.setEnableLoadMore(false);
                        getBinding().recommendationsStatusView.showNoNetwork();
                    }

                    @Override
                    public void onSQLiteError() {
                    }
                }, getModel().getCount());
            }
        });
        getModel().setTaskAdapter(new TaskListAdapter(getContext(), getModel().getLauncher(), position -> {

        }));
        getBinding().workbenchUncompleteTasksCardview.setOnClickListener(v -> {
            createIntent(UncompletedTaskListPage.class);
        });
    }

    @Override
    protected List<Runnable> loadWithChildThread() {
        List<Runnable> list = new ArrayList<>();
        list.add(() -> TaskUtil.getRecommendationTasks(new TaskListCallback() {
            @Override
            public void onSuccess(List<Task> tasks, boolean hasMore) {
                runOnUiThread(() -> getModel().getTaskAdapter().refreshItems(tasks));
            }

            @Override
            public void unknownError(String errorCode) {
                ToastUtil.info("拉取推荐任务失败"+errorCode);
                getBinding().smartRefreshLayout.setEnableLoadMore(false);
                runOnUiThread(()->getBinding().recommendationsStatusView.showError());
            }

            @Override
            public void onIOException() {
                ToastUtil.toast("服务器无响应");
                getBinding().smartRefreshLayout.setEnableLoadMore(false);
                runOnUiThread(()->getBinding().recommendationsStatusView.showNoNetwork());
            }

            @Override
            public void onSQLiteError() {
            }
        }, getModel().getCount()));
        return list;
    }

    @Override
    protected void loadSuccess() {

    }
}