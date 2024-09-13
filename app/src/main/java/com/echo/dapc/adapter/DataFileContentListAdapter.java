package com.echo.dapc.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.echo.dapc.R;
import com.echo.dapc.adapter.viewholder.DataFileTextContentViewHolder;
import com.echo.dapc.base.adapter.BaseRecycleListAdapter;

import java.util.ArrayList;
import java.util.List;

public class DataFileContentListAdapter extends BaseRecycleListAdapter<String, DataFileTextContentViewHolder> {
    public DataFileContentListAdapter() {
        this(new ArrayList<>());
    }

    public DataFileContentListAdapter(List<String> data) {
        super(data);
    }

    @Override
    protected int getViewLayoutId(int viewType) {
        return R.layout.datafile_text_content;
    }

    @NonNull
    @Override
    protected DataFileTextContentViewHolder createViewHolder(@NonNull View view, int viewType) {
        return new DataFileTextContentViewHolder(view);
    }

    @Override
    protected void bindViewHolder(@NonNull String s, @NonNull DataFileTextContentViewHolder viewHolder) {
        viewHolder.content.setText(s);
    }
}
