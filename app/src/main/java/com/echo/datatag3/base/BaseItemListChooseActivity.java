package com.echo.datatag3.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

public abstract class BaseItemListChooseActivity<T, VH extends RecyclerView.ViewHolder> extends BaseItemListActivity<T, VH>{

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
    protected abstract BaseRecycleViewAdapter.OnItemClickListener createOnItemClickListener();

    @Override
    protected void init() {
        getModel().setPage(1);
        getBinding().listTbTitle.setTitle(getTitleString());
        getBinding().listTbTitle.setOnTitleBarListener(getTitleBarListener());
        BaseItemListAdapter<T, VH> adapter = createAdapter();
        BaseRecycleViewAdapter.OnItemClickListener onItemClickListener = createOnItemClickListener();
        BaseRecycleViewAdapter.OnItemLongClickListener createLongClickListener = createLongClickListener();
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
}
