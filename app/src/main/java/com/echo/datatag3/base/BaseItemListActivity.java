package com.echo.datatag3.base;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.datatag3.R;
import com.echo.datatag3.databinding.ActivityItemListBinding;
import com.echo.datatag3.mvvm.model.ItemListModel;
import com.echo.datatag3.mvvm.viewmodel.ItemListViewModel;
import com.echo.datatag3.util.GlobalConstant;
import com.hjq.bar.OnTitleBarListener;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Collection;
import java.util.List;

// extends StickyJudge
public abstract class BaseItemListActivity<T, VH extends RecyclerView.ViewHolder> extends BaseActivity<ItemListViewModel<T, VH>, ItemListModel<T, VH>, ActivityItemListBinding> {

    private Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    @Override
    protected final int getContentViewId() {
        return R.layout.activity_item_list;
    }

    @Override
    protected final ItemListViewModel<T, VH> createViewModel() {
        return (ItemListViewModel<T, VH>) new ViewModelProvider(this).get(ItemListViewModel.class);
    }

    @Override
    protected final ItemListModel<T, VH> createModel() throws InstantiationException {
        return new ItemListModel<T, VH>();
    }

    @Override
    protected void handleRes(int resultCode, Intent data) {
        if (resultCode == GlobalConstant.LIST_AUTO_REFRESH) {
            getViewModel().getModel().getHandler().postDelayed(() -> getBinding().listSrl.autoRefresh(), 500);
        }else if (resultCode == GlobalConstant.LIST_REMOVE_ITEM){
            int position = getModel().getLastClickPosition();
            getModel().setLastClickPosition(-1);
            removeItem(position);
        }
    }

    protected abstract @NonNull String getTitleString();

    protected abstract @NonNull BaseItemListAdapter<T, VH> createAdapter();

    protected abstract @NonNull OnTitleBarListener getTitleBarListener();

    protected abstract void initLoading();

    protected abstract @NonNull OnRefreshLoadMoreListener createRefreshLoadMoreListener();

    protected BaseRecycleViewAdapter.OnItemClickListener createOnItemClickListener() {
        return null;
    }

    protected BaseRecycleViewAdapter.OnItemLongClickListener createLongClickListener() {
        return null;
    }

    protected final void showEmpty() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            getBinding().statusView.showEmpty();
            noMore();
        }else {
            MAIN_HANDLER.post(() -> {
                getBinding().statusView.showEmpty();
                noMore();
            });
        }
    }

    protected final void showError() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            getBinding().statusView.showError();
            noMore();
        }else {
            MAIN_HANDLER.post(() -> {
                getBinding().statusView.showError();
                noMore();
            });
        }
    }

    protected final void showLoading() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            getBinding().statusView.showLoading();
        }else {
            MAIN_HANDLER.post(() -> getBinding().statusView.showLoading());
        }
    }

    protected final void showNoNetwork() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            getBinding().statusView.showNoNetwork();
            noMore();
        }else {
            MAIN_HANDLER.post(() -> {
                getBinding().statusView.showNoNetwork();
                noMore();
            });
        }
    }

    protected final void showContent() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            getBinding().statusView.showContent();
        }else {
            MAIN_HANDLER.post(() -> getBinding().statusView.showContent());
        }
    }

    protected final void showItems(List<T> list) {
        showItems(list, true);
    }

    protected final void showItems(List<T> list, boolean canContinueRefresh) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            if (list == null || list.isEmpty()) {
                getBinding().statusView.showEmpty();
            } else {
                getBinding().statusView.showContent();
                refreshItems(list);
            }
            if (!canContinueRefresh) {
                noMore();
            } else {
                getModel().addPage();
            }
        }else {
            MAIN_HANDLER.post(() -> {
                if (list == null || list.isEmpty()) {
                    getBinding().statusView.showEmpty();
                } else {
                    getBinding().statusView.showContent();
                    refreshItems(list);
                }
                if (!canContinueRefresh) {
                    noMore();
                } else {
                    getModel().addPage();
                }
            });
        }
    }

    @Override
    protected void init() {
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

    protected final T getLastClickEntity(){
        if (getModel().getLastClickPosition()==-1)return null;
        else return getItem(getModel().getLastClickPosition());
    }

    protected final int getLastClickPosition(){
        return getModel().getLastClickPosition();
    }

    protected final List<T> getItems(){
        return getViewModel().getModel().getAdapter().getData();
    }

    protected final T getItem(int position){
        return getViewModel().getModel().getAdapter().getItem(position);
    }

    protected final void addItem(T t) {
        showContent();
        if (Looper.getMainLooper() == Looper.myLooper()) {
            getViewModel().getModel().getAdapter().addItem(t);
        }else {
            MAIN_HANDLER.post(() -> {
                getViewModel().getModel().getAdapter().addItem(t);
            });
        }
    }

    protected final void addItem(T t, int position) {
        showContent();
        if (Looper.getMainLooper() == Looper.myLooper()) {
            getViewModel().getModel().getAdapter().addItem(t, position);
        }else {
            MAIN_HANDLER.post(() -> {
                getViewModel().getModel().getAdapter().addItem(t, position);
            });
        }
    }

    protected final void addItems(Collection<T> collection, int position) {
        showContent();
        if (Looper.getMainLooper() == Looper.myLooper()) {
            getViewModel().getModel().getAdapter().addItems(collection, position);
        }else {
            MAIN_HANDLER.post(() -> {
                getViewModel().getModel().getAdapter().addItems(collection, position);
            });
        }
    }

    protected final void addItems(Collection<T> collection, int position, boolean canContinueRefresh) {
        showContent();
        if (Looper.getMainLooper() == Looper.myLooper()) {
            getViewModel().getModel().getAdapter().addItems(collection, position);
            if (!canContinueRefresh){
                noMore();
            }
        }else {
            MAIN_HANDLER.post(() -> {
                getViewModel().getModel().getAdapter().addItems(collection, position);
                if (!canContinueRefresh){
                    noMore();
                }
            });
        }
    }

    protected final void removeItem(int position) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            getViewModel().getModel().getAdapter().removeItem(position);
        }else {
            MAIN_HANDLER.post(() -> {
                getViewModel().getModel().getAdapter().removeItem(position);
            });
        }
    }

    protected final void removeAllItems() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            getViewModel().getModel().getAdapter().removeAllItems();
        }else {
            MAIN_HANDLER.post(() -> {
                getViewModel().getModel().getAdapter().removeAllItems();
            });
        }
    }

    protected final void changeItem(T t, int position) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            getViewModel().getModel().getAdapter().changeItem(t, position);
        }else {
            MAIN_HANDLER.post(() -> {
                getViewModel().getModel().getAdapter().changeItem(t, position);
            });
        }
    }

    protected final void refreshItem(T t, int position) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            getViewModel().getModel().getAdapter().refreshItem(t, position);
        }else {
            MAIN_HANDLER.post(() -> {
                getViewModel().getModel().getAdapter().refreshItem(t, position);
            });
        }
    }

    protected final void refreshItems(Collection<T> collection) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            if (collection != null) getViewModel().getModel().getAdapter().refreshItems(collection);
        }else {
            MAIN_HANDLER.post(() -> {
                if (collection != null) getViewModel().getModel().getAdapter().refreshItems(collection);
            });
        }
    }

    protected final void noMore(){
        getBinding().listSrl.setEnableLoadMore(false);
    }

    protected final void noRefresh(){
        getBinding().listSrl.setEnableRefresh(false);
    }

    protected final int getItemCount(){
        return getModel().getAdapter().getItemCount();
    }

    protected final void scrollToTop(){
        getBinding().listRecycler.scrollToPosition(0);
    }

    protected final void scrollToBottom(){
        getBinding().listRecycler.scrollToPosition(getItemCount()-1);
    }
}
