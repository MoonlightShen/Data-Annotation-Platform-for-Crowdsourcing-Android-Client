package com.echo.datatag3.mvvm.view.list;

import androidx.annotation.NonNull;

import com.echo.datatag3.R;
import com.echo.datatag3.adapter.FriendRequestListAdapter;
import com.echo.datatag3.adapter.viewholder.FriendApplicationViewHolder;
import com.echo.datatag3.base.BaseItemListActivity;
import com.echo.datatag3.base.BaseItemListAdapter;
import com.echo.datatag3.base.BaseRecycleViewAdapter;
import com.echo.datatag3.bean.entity.FriendRequest;
import com.echo.datatag3.bean.logic.User;
import com.echo.datatag3.interfaces.callback.friendapplication.LoadAllFriendApplicationsCallback;
import com.echo.datatag3.interfaces.callback.user.QueryUsersCallback;
import com.echo.datatag3.mvvm.view.FriendRequestActivity;
import com.echo.datatag3.mvvm.view.FriendRequestCreateActivity;
import com.echo.datatag3.util.business.FriendRequestUtil;
import com.echo.datatag3.util.business.UserUtil;
import com.echo.datatag3.util.kt.DialogUtil;
import com.echo.datatag3.util.system.JsonUtil;
import com.echo.datatag3.util.system.ToastUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

public class FriendRequestListActivity extends BaseItemListActivity<FriendRequest, FriendApplicationViewHolder> {

    @NonNull
    @Override
    protected String getTitleString() {
        return "好友申请";
    }

    @Override
    protected BaseRecycleViewAdapter.OnItemClickListener createOnItemClickListener() {
        return (itemview, position) -> {
            createIntent(FriendRequestActivity.class, "friend_request", JsonUtil.toJson(getItem(position)));
        };
    }

    @NonNull
    @Override
    protected BaseItemListAdapter<FriendRequest, FriendApplicationViewHolder> createAdapter() {
        return new FriendRequestListAdapter();
    }

    @NonNull
    @Override
    protected OnTitleBarListener getTitleBarListener() {
        return new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }
            @Override
            public void onRightClick(TitleBar titleBar) {
                DialogUtil.searchUser(getContext(), (searchText, statusView, adapter) -> {
                    statusView.showLoading();
                    UserUtil.queryUserByName(new QueryUsersCallback() {
                        @Override
                        public void onSuccess(List<User> users) {
                            if (adapter.getClickListener()==null){
                                adapter.setClickListener((itemview, position) -> createIntent(FriendRequestCreateActivity.class, "user_id", adapter.getData().get(position).getUserId()));
                            }
                            runOnUiThread(() -> {
                                if (users != null && !users.isEmpty()) statusView.showContent();
                                else statusView.showEmpty();
                                adapter.refreshItems(users);
                            });
                        }

                        @Override
                        public void onError(String error) {
                            runOnUiThread(statusView::showError);
                        }
                    }, searchText);
                });
            }
        };
    }

    @Override
    protected void initLoading() {
        FriendRequestUtil.loadAllFriendApplications(new LoadAllFriendApplicationsCallback() {
            @Override
            public void onSuccess(List<FriendRequest> requests) {
                if (requests.isEmpty()){
                    showEmpty();
                }else {
                    runOnUiThread(() -> {
                        showContent();
                        refreshItems(requests);
                    });
                }
            }

            @Override
            public void onSQLiteError() {
                ToastUtil.error("拉取失败");
                showError();
            }

            @Override
            public void onIOException() {
                ToastUtil.error("拉取失败");
                showError();
            }
        });
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
                refreshLayout.finishRefresh();
                FriendRequestUtil.loadAllFriendApplications(new LoadAllFriendApplicationsCallback() {
                    @Override
                    public void onSuccess(List<FriendRequest> requests) {
                        if (requests.isEmpty()){
                            showEmpty();
                        }else {
                            runOnUiThread(() -> {
                                showContent();
                                refreshItems(requests);
                            });
                        }
                    }

                    @Override
                    public void onSQLiteError() {
                        ToastUtil.error("拉取失败");
                        showError();
                    }

                    @Override
                    public void onIOException() {
                        ToastUtil.error("拉取失败");
                        showError();
                    }
                });
            }
        };
    }

    @Override
    protected void init() {
        super.init();
        getBinding().listTbTitle.setRightIcon(R.drawable.common_add_friend_gray);
        getBinding().listSrl.setEnableLoadMore(false);
    }


}