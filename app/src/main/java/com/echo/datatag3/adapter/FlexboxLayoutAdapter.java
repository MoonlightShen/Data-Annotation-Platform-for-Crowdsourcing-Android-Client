package com.echo.datatag3.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseRecycleViewAdapter;
import com.echo.datatag3.bean.ui.FlexBoxItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FlexboxLayoutAdapter extends BaseRecycleViewAdapter<FlexBoxItem, FlexboxLayoutAdapter.FlexboxItemViewHolder> {

    private boolean multiSelectMode;
    private boolean cancelable;

    private final Set<Integer> multiSelectItems = new HashSet<>();
    private int selectPosition=-1;

    public FlexboxLayoutAdapter() {
        this(new ArrayList<>());
    }

    public FlexboxLayoutAdapter(List<FlexBoxItem> data) {
        super(data);
    }

    @Override
    protected int getViewLayoutId(int viewType) {
        return R.layout.flex_box_item;
    }

    @NonNull
    @Override
    protected FlexboxItemViewHolder createViewHolder(@NonNull View view, int viewType) {
        return new FlexboxItemViewHolder(view);
    }

    @Override
    protected void bindViewHolder(@NonNull FlexBoxItem flexBoxItem, @NonNull FlexboxItemViewHolder viewHolder) {
        viewHolder.content.setText(flexBoxItem.getContent());
    }

    public void clearSelect() {
        if (multiSelectMode) {
            for (Integer select:new HashSet<>(multiSelectItems)){
                select(select);
            }
            multiSelectItems.clear();
        }
        else {
            select(selectPosition);
            selectPosition = -1;
        }
    }

    public void select(int position) {
        if (multiSelectMode) multiSelect(position);
        else singleSelect(position);
    }

    private void multiSelect(int position) {
        if (multiSelectMode) {
            if (multiSelectItems.contains(position)) multiSelectItems.remove(position);
            else multiSelectItems.add(position);
            notifyItemChanged(position);
        }
    }

    private void singleSelect(int position) {
        if (position == selectPosition) {
            if (cancelable) {
                selectPosition = -1;
            }
        } else {
            selectPosition = position;
        }
    }

    public List<Integer> getSelectIndex(){
        if (multiSelectMode){
            return new ArrayList<>(multiSelectItems);
        }else{
            return new ArrayList<>(selectPosition);
        }
    }

    public static class FlexboxItemViewHolder extends RecyclerView.ViewHolder {
        TextView content;

        public FlexboxItemViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
        }
    }

    public boolean isMultiSelectMode() {
        return multiSelectMode;
    }

    public void setMultiSelectMode(boolean multiSelectMode) {
        this.multiSelectMode = multiSelectMode;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }
}
