package com.echo.dapc.base.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.List;

public abstract class BaseRecycleListAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private final List<T> mData;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;
    private boolean clickable = false;
    private boolean longClickable = false;

    public BaseRecycleListAdapter(List<T> data) {
        this.mData = data;
    }

    public List<T> getData() {
        return mData;
    }

    protected void setData(List<T> data) {
        if (!data.isEmpty()) {
            this.mData.clear();
            this.mData.addAll(data);
        }
    }

    protected abstract @LayoutRes int getViewLayoutId(int viewType);

    protected abstract @NonNull VH createViewHolder(@NonNull View view, int viewType);

    protected abstract void bindViewHolder(@NonNull T t, @NonNull VH viewHolder);

    protected boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    protected boolean isLongClickable() {
        return longClickable;
    }

    public void setLongClickable(boolean longClickable) {
        this.longClickable = longClickable;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getViewLayoutId(viewType), parent, false);
        if (mClickListener != null) {
            clickable = true;
            view.setOnClickListener(v -> {
                if (null != mClickListener && clickable) {
                    mClickListener.onItemClick(v, (int) v.getTag());
                }
            });
        }
        if (mLongClickListener != null) {
            longClickable = true;
            view.setOnLongClickListener(v -> {
                if (null != mLongClickListener && longClickable) {
                    mLongClickListener.onItemLongClick(v, (int) v.getTag());
                    return true;
                }
                return false;
            });
        }
        return createViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VH viewHolder, int position) {
        T t = getData().get(position);
        bindViewHolder(t, viewHolder);
        viewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public void addItem(T t, int position) {
        mData.add(position, t);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void addItem(T t) {
        addItem(t, getItemCount());
    }

    public void addItems(Collection<T> collection) {
        addItems(collection, getItemCount());
    }

    public void addItems(Collection<T> collection, int position) {
        mData.addAll(position, collection);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeAllItems() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void changeItem(T t, int position) {
        mData.set(position, t);
        notifyItemChanged(position);
    }

    public void refreshItem(T t, int position) {
        mData.set(position, t);
        notifyItemChanged(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void refreshItems(Collection<T> collection) {
        if (collection != null) {
            mData.clear();
            mData.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public OnItemClickListener getClickListener() {
        return mClickListener;
    }

    public void setClickListener(OnItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public OnItemLongClickListener getLongClickListener() {
        return mLongClickListener;
    }

    public void setLongClickListener(OnItemLongClickListener mLongClickListener) {
        this.mLongClickListener = mLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemview, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemview, int position);
    }
}
