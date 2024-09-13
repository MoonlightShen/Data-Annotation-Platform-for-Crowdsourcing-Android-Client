package com.echo.dapc.mvvm.view.list;

import androidx.annotation.NonNull;

import com.echo.dapc.R;
import com.echo.dapc.adapter.FriendRequestListAdapter;
import com.echo.dapc.adapter.viewholder.FriendApplicationViewHolder;
import com.echo.dapc.base.activity.BaseItemListActivity;
import com.echo.dapc.base.adapter.BaseRecycleListAdapter;
import com.echo.dapc.bean.entity.FriendRequest;
import com.echo.dapc.bean.logic.User;
import com.echo.dapc.interfaces.callback.friendapplication.LoadAllFriendApplicationsCallback;
import com.echo.dapc.interfaces.callback.user.QueryUsersCallback;
import com.echo.dapc.mvvm.view.FriendRequestActivity;
import com.echo.dapc.mvvm.view.FriendRequestCreateActivity;
import com.echo.dapc.util.business.FriendRequestUtil;
import com.echo.dapc.util.business.UserUtil;
import com.echo.dapc.util.kt.DialogUtil;
import com.echo.dapc.util.system.JsonUtil;
import com.echo.dapc.util.system.ToastUtil;
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
    protected BaseRecycleListAdapter.OnItemClickListener createOnItemClickListener() {
        return (itemview, position) -> {
            createIntent(FriendRequestActivity.class, false, new IntentData("friend_request", JsonUtil.toJson(getItem(position))));
        };
    }

    @NonNull
    @Override
    protected BaseRecycleListAdapter<FriendRequest, FriendApplicationViewHolder> createAdapter() {
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
                                adapter.setClickListener((itemview, position) -> createIntent(FriendRequestCreateActivity.class,false, new IntentData( "user_id", adapter.getData().get(position).getUserId())));
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
    protected void initActivity() {
        super.initActivity();
        getBinding().listTbTitle.setRightIcon(R.drawable.common_add_friend_gray);
        getBinding().listSrl.setEnableLoadMore(false);
    }
}