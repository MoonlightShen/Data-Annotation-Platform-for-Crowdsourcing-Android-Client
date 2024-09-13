package com.echo.dapc.mvvm.view.list;

import androidx.annotation.NonNull;

import com.echo.dapc.adapter.TeamNoticeListAdapter;
import com.echo.dapc.adapter.viewholder.TeamNoticeViewHolder;
import com.echo.dapc.base.activity.BaseItemListActivity;
import com.echo.dapc.base.adapter.BaseRecycleListAdapter;
import com.echo.dapc.bean.entity.TeamNotice;
import com.echo.dapc.interfaces.callback.teamnotice.LoadAllTeamNoticesCallback;
import com.echo.dapc.util.business.TeamNoticeUtil;
import com.echo.dapc.util.system.ToastUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

public class TeamNoticeListActivity extends BaseItemListActivity<TeamNotice, TeamNoticeViewHolder> {

    @NonNull
    @Override
    protected String getTitleString() {
        return "团队通知";
    }

    @NonNull
    @Override
    protected BaseRecycleListAdapter<TeamNotice, TeamNoticeViewHolder> createAdapter() {
        return new TeamNoticeListAdapter();
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
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                TeamNoticeUtil.loadAllTeamNotices(new LoadAllTeamNoticesCallback() {
                    @Override
                    public void onSuccess(List<TeamNotice> notices) {
                        runOnUiThread(() -> refreshItems(notices));
                        if (notices.isEmpty()){
                            showEmpty();
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
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
        getBinding().listTbTitle.setRightIcon(null);
        getBinding().listSrl.setEnableLoadMore(false);
    }
}