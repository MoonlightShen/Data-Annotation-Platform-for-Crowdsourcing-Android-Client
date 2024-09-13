package com.echo.dapc.base.activity;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.classic.common.MultipleStatusView;
import com.echo.dapc.R;
import com.echo.dapc.base.adapter.BaseRecycleListAdapter;
import com.echo.dapc.databinding.ActivityItemListBinding;
import com.echo.dapc.mvvm.model.ItemListModel;
import com.echo.dapc.mvvm.viewmodel.ItemListViewModel;
import com.echo.dapc.util.GlobalConstant;
import com.echo.dapc.util.system.ThreadUtil;
import com.hjq.bar.OnTitleBarListener;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Collection;
import java.util.List;

public abstract class BaseItemListActivity<T, VH extends RecyclerView.ViewHolder> extends BaseDataBindingActivity<ItemListViewModel<T, VH>, ItemListModel<T, VH>, ActivityItemListBinding> {

    @Override
    protected final int getUILayoutId() {
        return R.layout.activity_item_list;
    }

    @Override
    protected final ItemListViewModel<T, VH> createViewModel() {
        return (ItemListViewModel<T, VH>) new ViewModelProvider(this).get(ItemListViewModel.class);
    }

    @Override
    protected final ItemListModel<T, VH> createModel() {
        return new ItemListModel<>();
    }

    @Override
    protected void handleRes(int resultCode, Intent data) {
        if (resultCode == GlobalConstant.LIST_AUTO_REFRESH) {
            getViewModel().getModel().getHandler().postDelayed(() -> getBinding().listSrl.autoRefresh(), 500);
        } else if (resultCode == GlobalConstant.LIST_REMOVE_ITEM) {
            int position = getModel().getLastClickPosition();
            getModel().setLastClickPosition(-1);
            removeItem(position);
        }
    }

    protected abstract @NonNull String getTitleString();

    protected abstract @NonNull BaseRecycleListAdapter<T, VH> createAdapter();

    protected abstract @NonNull OnTitleBarListener getTitleBarListener();

    protected abstract void initLoading();

    protected abstract @NonNull OnRefreshLoadMoreListener createRefreshLoadMoreListener();

    protected BaseRecycleListAdapter.OnItemClickListener createOnItemClickListener() {
        return null;
    }

    protected BaseRecycleListAdapter.OnItemLongClickListener createLongClickListener() {
        return null;
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

    protected final void showEmpty() {
        ThreadUtil.runOnMain(() -> {
            getBinding().statusView.showEmpty();
            noMore();
        });
    }

    protected final void showError() {
        ThreadUtil.runOnMain(() -> {
            getBinding().statusView.showError();
            noMore();
        });
    }

    protected final void showLoading() {
        ThreadUtil.runOnMain(() -> {
            getBinding().statusView.showLoading();
        });
    }

    protected final void showNoNetwork() {
        ThreadUtil.runOnMain(() -> {
            getBinding().statusView.showNoNetwork();
            noMore();
        });
    }

    protected final void showContent() {
        ThreadUtil.runOnMain(() -> {
            if (getBinding().statusView.getViewStatus()== MultipleStatusView.STATUS_CONTENT)getBinding().statusView.showContent();
        });
    }

    protected final void showItems(List<T> list) {
        showItems(list, true);
    }

    protected final void showItems(List<T> list, boolean noMore) {
        ThreadUtil.runOnMain(() -> {
            if (list == null || list.isEmpty()) {
                getBinding().statusView.showEmpty();
            } else {
                getBinding().statusView.showContent();
                refreshItems(list);
            }
            if (!noMore) {
                noMore();
            } else {
                getModel().addPage();
            }
        });
    }

    @Override
    protected void initActivity() {
        super.initActivity();
        getBinding().listTbTitle.setTitle(getTitleString());
        getBinding().listTbTitle.setOnTitleBarListener(getTitleBarListener());

        BaseRecycleListAdapter<T, VH> adapter = createAdapter();
        BaseRecycleListAdapter.OnItemClickListener onItemClickListener = createOnItemClickListener();
        BaseRecycleListAdapter.OnItemLongClickListener createLongClickListener = createLongClickListener();
        if (onItemClickListener != null) {
            adapter.setClickListener(onItemClickListener);
        }
        if (createLongClickListener != null)
            adapter.setLongClickListener(createLongClickListener);
        getViewModel().getModel().setAdapter(adapter);
        getBinding().listRecycler.setAdapter(getViewModel().getModel().getAdapter());

//        getBinding().listRecycler.addItemDecoration(new StickyHeaderDecoration(new StickyHeaderDecoration.GroupInfoCallback() {
//            @Override
//            public GroupInfo getGroupInfo(int position) {
//                return null;
//            }
//        }));
        //TODO 吸顶
//        BaseStickyListAdapter<T, VH> adapter = new BaseStickyListAdapter<T, VH>();
//        BaseRecycleViewAdapter.OnItemClickListener onItemClickListener = createOnItemClickListener();
//        BaseRecycleViewAdapter.OnItemLongClickListener createLongClickListener = createLongClickListener();
//        if (onItemClickListener != null){
//            adapter.setClickListener(onItemClickListener);
//        }
//        if (createLongClickListener != null)
//            adapter.setLongClickListener(createLongClickListener);
//
//        getBinding().stickyContainer.setOnStickyPositionChangedListener(position -> {
//            T item = getItem(position);
//            if (item != null) {
//                getBinding().stickyTitle.setText(item.getStickyTitle());
//            }
//        });
//        StickyItemDecoration stickyItemDecoration = new StickyItemDecoration(getBinding().stickyContainer, StickyListAdapter.TYPE_HEAD_STICKY);
//        recyclerView.addItemDecoration(stickyItemDecoration);
//        recyclerView.setAdapter(mAdapter = new StickyListAdapter());


        RefreshLayout refreshLayout = getBinding().listSrl;
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshLoadMoreListener(createRefreshLoadMoreListener());
//        getBinding().stickyContainer.setOnStickyPositionChangedListener(new StickyHeadContainer.OnStickyPositionChangedListener() {
//            @Override
//            public void onPositionChanged(int position) {
//
//            }
//        });
        showLoading();
        initLoading();
    }

    protected final T getLastClickEntity() {
        if (getModel().getLastClickPosition() == -1) return null;
        else return getItem(getModel().getLastClickPosition());
    }

    protected final int getLastClickPosition() {
        return getModel().getLastClickPosition();
    }

    protected final List<T> getItems() {
        return getViewModel().getModel().getAdapter().getData();
    }

    protected final T getItem(int position) {
        return getViewModel().getModel().getAdapter().getItem(position);
    }

    protected final void addItem(T t) {
        ThreadUtil.runOnMain(() -> {
            showContent();
            getViewModel().getModel().getAdapter().addItem(t);
        });
    }

    protected final void addItem(T t, int position) {
        ThreadUtil.runOnMain(() -> {
            showContent();
            getViewModel().getModel().getAdapter().addItem(t, position);
        });
    }

    protected final void addItems(Collection<T> collection, int position) {
        ThreadUtil.runOnMain(() -> {
            showContent();
            getViewModel().getModel().getAdapter().addItems(collection, position);
        });
    }

    protected final void addItems(Collection<T> collection, int position, boolean noMore) {
        ThreadUtil.runOnMain(() -> {
            showContent();
            getViewModel().getModel().getAdapter().addItems(collection, position);
            if (!noMore) {
                noMore();
            }
        });
    }

    protected final void removeItem(int position) {
        ThreadUtil.runOnMain(() -> {
            showContent();
            getViewModel().getModel().getAdapter().removeItem(position);
        });
    }

    protected final void removeAllItems() {
        ThreadUtil.runOnMain(() -> {
            showContent();
            getViewModel().getModel().getAdapter().removeAllItems();
        });
    }

    protected final void changeItem(T t, int position) {
        ThreadUtil.runOnMain(() -> {
            showContent();
            getViewModel().getModel().getAdapter().changeItem(t, position);
        });
    }

    protected final void refreshItem(T t, int position) {
        ThreadUtil.runOnMain(() -> {
            showContent();
            getViewModel().getModel().getAdapter().refreshItem(t, position);
        });
    }

    protected final void refreshItems(Collection<T> collection) {
        ThreadUtil.runOnMain(() -> {
            showContent();
            if (collection != null) getViewModel().getModel().getAdapter().refreshItems(collection);
        });
    }

    protected final void noMore() {
        getBinding().listSrl.setEnableLoadMore(false);
    }

    protected final void noRefresh() {
        getBinding().listSrl.setEnableRefresh(false);
    }

    protected final int getItemCount() {
        return getModel().getAdapter().getItemCount();
    }

    protected final void scrollToTop() {
        getBinding().listRecycler.scrollToPosition(0);
    }

    protected final void scrollToBottom() {
        getBinding().listRecycler.scrollToPosition(getItemCount() - 1);
    }
}
