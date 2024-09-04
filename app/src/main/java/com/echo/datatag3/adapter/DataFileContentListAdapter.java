package com.echo.datatag3.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.echo.datatag3.R;
import com.echo.datatag3.adapter.viewholder.DataFileTextContentViewHolder;
import com.echo.datatag3.base.BaseItemListAdapter;

import java.util.ArrayList;
import java.util.List;

public class DataFileContentListAdapter extends BaseItemListAdapter<String, DataFileTextContentViewHolder> {
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
