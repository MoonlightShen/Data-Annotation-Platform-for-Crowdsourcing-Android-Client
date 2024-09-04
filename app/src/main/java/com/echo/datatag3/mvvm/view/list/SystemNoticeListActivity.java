package com.echo.datatag3.mvvm.view.list;

import androidx.annotation.NonNull;

import com.echo.datatag3.adapter.SystemNoticeListAdapter;
import com.echo.datatag3.adapter.viewholder.SystemNoticeViewHolder;
import com.echo.datatag3.base.BaseItemListActivity;
import com.echo.datatag3.base.BaseItemListAdapter;
import com.echo.datatag3.bean.entity.LocalSystemNotice;
import com.echo.datatag3.bean.logic.SystemNotice;
import com.echo.datatag3.interfaces.callback.common.SQLiteDBQueryCallback;
import com.echo.datatag3.util.business.SystemNoticeUtil;
import com.echo.datatag3.util.system.ToastUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

public class SystemNoticeListActivity extends BaseItemListActivity<SystemNotice, SystemNoticeViewHolder> {
    @NonNull
    @Override
    protected String getTitleString() {
        return "系统通知";
    }

    @NonNull
    @Override
    protected BaseItemListAdapter<SystemNotice, SystemNoticeViewHolder> createAdapter() {
        return new SystemNoticeListAdapter();
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
        SystemNoticeUtil.loadSystemNotice(new SQLiteDBQueryCallback<>() {
            @Override
            public void onSuccess(List<SystemNotice> list) {
                showItems(list);
            }

            @Override
            public void onSQLiteDBError() {
                showError();
                ToastUtil.error("加载失败");
            }
        }, 0, getModel().getPageSize());
    }

    @NonNull
    @Override
    protected OnRefreshLoadMoreListener createRefreshLoadMoreListener() {
        return new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                SystemNoticeUtil.loadSystemNotice(new SQLiteDBQueryCallback<>() {
                    @Override
                    public void onSuccess(List<SystemNotice> list) {
                        refreshLayout.finishLoadMore();
                        showItems(list);
                    }

                    @Override
                    public void onSQLiteDBError() {
                        refreshLayout.finishLoadMore();
                        showError();
                        ToastUtil.error("加载失败");
                    }
                }, getModel().getPage(), getModel().getPageSize());
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (getLastClickEntity()!=null){
                    SystemNoticeUtil.querySystemNotice(new SQLiteDBQueryCallback<>() {
                        @Override
                        public void onSuccess(List<SystemNotice> list) {
                            refreshLayout.finishRefresh();
                            if (list != null && !list.isEmpty()) {
                                refreshItem(list.get(0), getLastClickPosition());
                            }
                        }

                        @Override
                        public void onSQLiteDBError() {
                            refreshLayout.finishRefresh();
                            showError();
                            ToastUtil.error("刷新失败");
                        }
                    }, getLastClickEntity().getNoticeIndex());
                }else {
                    refreshLayout.finishRefresh();
                }
            }
        };
    }

    @Override
    protected void init() {
        super.init();
        noRefresh();
    }
}
