package com.echo.dapc.mvvm.view.fragment;

import androidx.annotation.NonNull;

import com.echo.dapc.R;
import com.echo.dapc.adapter.TaskListAdapter;
import com.echo.dapc.base.BaseFragment;
import com.echo.dapc.bean.logic.Task;
import com.echo.dapc.databinding.FragmentWorkbenchBinding;
import com.echo.dapc.interfaces.callback.task.TaskListCallback;
import com.echo.dapc.mvvm.model.WorkBenchModel;
import com.echo.dapc.mvvm.view.task.UncompletedTaskListActivity;
import com.echo.dapc.mvvm.viewmodel.WorkBenchViewModel;
import com.echo.dapc.util.business.TaskUtil;
import com.echo.dapc.util.system.ToastUtil;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;


public class WorkBenchFragment extends BaseFragment<WorkBenchViewModel, WorkBenchModel, FragmentWorkbenchBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_workbench;
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
                        ToastUtil.normal("服务器无响应");
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
                        ToastUtil.normal("服务器无响应");
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
            createIntent(UncompletedTaskListActivity.class);
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
                ToastUtil.normal("服务器无响应");
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