package com.echo.datatag3.mvvm.view.list;

import androidx.annotation.NonNull;

import com.echo.datatag3.adapter.TeamNoticeListAdapter;
import com.echo.datatag3.adapter.viewholder.TeamNoticeViewHolder;
import com.echo.datatag3.base.BaseItemListActivity;
import com.echo.datatag3.base.BaseItemListAdapter;
import com.echo.datatag3.bean.entity.TeamNotice;
import com.echo.datatag3.interfaces.callback.teamnotice.LoadAllTeamNoticesCallback;
import com.echo.datatag3.util.business.TeamNoticeUtil;
import com.echo.datatag3.util.system.ToastUtil;
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
    protected BaseItemListAdapter<TeamNotice, TeamNoticeViewHolder> createAdapter() {
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
    protected void init() {
        super.init();
        getBinding().listTbTitle.setRightIcon(null);
        getBinding().listSrl.setEnableLoadMore(false);
    }
}