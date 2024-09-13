package com.echo.dapc.base;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.echo.dapc.base.adapter.BaseRecycleListAdapter;
import com.echo.dapc.widget.stickyhead.StickyJudge;

import java.util.ArrayList;
import java.util.List;

//TODO 具体实现
public class BaseStickyListAdapter<T extends StickyJudge, VH extends RecyclerView.ViewHolder> extends BaseRecycleListAdapter<T, VH> {
    public BaseStickyListAdapter() {
        this(new ArrayList<>());
    }

    public BaseStickyListAdapter(List<T> data) {
        super(data);
    }

    @Override
    protected int getViewLayoutId(int viewType) {
        return 0;
    }

    @NonNull
    @Override
    protected VH createViewHolder(@NonNull View view, int viewType) {
        return null;
    }

    @Override
    protected void bindViewHolder(@NonNull T t, @NonNull VH viewHolder) {

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof final GridLayoutManager gridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup oldSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItem(position).isStickyHead()) {
                        return gridLayoutManager.getSpanCount();
                    } else if (oldSizeLookup != null) {
                        return oldSizeLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VH holder) {
        final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp instanceof final StaggeredGridLayoutManager.LayoutParams slp) {
            slp.setFullSpan(getItem(holder.getLayoutPosition()).isStickyHead());
        }
    }

}
