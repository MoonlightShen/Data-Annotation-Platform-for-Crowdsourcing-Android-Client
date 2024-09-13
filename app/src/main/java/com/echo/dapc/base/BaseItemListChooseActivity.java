package com.echo.dapc.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.dapc.base.activity.BaseItemListActivity;
import com.echo.dapc.base.adapter.BaseRecycleListAdapter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

public abstract class BaseItemListChooseActivity<T, VH extends RecyclerView.ViewHolder> extends BaseItemListActivity<T, VH> {

    @NonNull
    @Override
    protected final OnRefreshLoadMoreListener createRefreshLoadMoreListener() {
        return new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        };
    }

    protected abstract @NonNull OnLoadMoreListener createOnLoadMoreListener();

    @Override
    protected abstract BaseRecycleListAdapter.OnItemClickListener createOnItemClickListener();

    @Override
    protected void initActivity() {
        super.initActivity();
        getModel().setPage(1);
        getBinding().listTbTitle.setTitle(getTitleString());
        getBinding().listTbTitle.setOnTitleBarListener(getTitleBarListener());
        BaseRecycleListAdapter<T, VH> adapter = createAdapter();
        BaseRecycleListAdapter.OnItemClickListener onItemClickListener = createOnItemClickListener();
        BaseRecycleListAdapter.OnItemLongClickListener createLongClickListener = createLongClickListener();
        if (onItemClickListener != null){
            adapter.setClickListener(onItemClickListener);
        }
        if (createLongClickListener != null)
            adapter.setLongClickListener(createLongClickListener);
        getViewModel().getModel().setAdapter(adapter);
        getBinding().listRecycler.setAdapter(getViewModel().getModel().getAdapter());
        RefreshLayout refreshLayout = getBinding().listSrl;
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadMoreListener(createOnLoadMoreListener());
        showLoading();
        initLoading();
        noRefresh();
    }

    /**
     * 在主线程的初始化操作，如为一些特殊组件添加监听器
     */
    @Override
    protected void initOnMainThread() {

    }

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    @Override
    protected void initInBackground() {

    }
}
