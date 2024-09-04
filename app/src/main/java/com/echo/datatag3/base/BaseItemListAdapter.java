package com.echo.datatag3.base;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseItemListAdapter<T, VH extends RecyclerView.ViewHolder> extends BaseRecycleViewAdapter<T, VH> {

    public BaseItemListAdapter(List<T> data) {
        super(data);
    }

}
