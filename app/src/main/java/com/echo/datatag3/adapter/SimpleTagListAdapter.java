package com.echo.datatag3.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseRecycleViewAdapter;
import com.echo.datatag3.bean.ui.FlexBoxItem;

import java.util.ArrayList;
import java.util.List;

public class SimpleTagListAdapter extends BaseRecycleViewAdapter<FlexBoxItem, SimpleTagListAdapter.TagViewHolder> {

    public SimpleTagListAdapter() {
        this(new ArrayList<>());
    }

    public SimpleTagListAdapter(List<FlexBoxItem> data) {
        super(data);
    }

    @Override
    protected int getViewLayoutId(int viewType) {
        return R.layout.flex_box_item;
    }

    @NonNull
    @Override
    protected SimpleTagListAdapter.TagViewHolder createViewHolder(@NonNull View view, int viewType) {
        return new SimpleTagListAdapter.TagViewHolder(view);
    }

    @Override
    protected void bindViewHolder(@NonNull FlexBoxItem flexBoxItem, @NonNull TagViewHolder viewHolder) {
        viewHolder.content.setText(flexBoxItem.getContent());
    }

    public static class TagViewHolder extends RecyclerView.ViewHolder {
        TextView content;

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
//            background = itemView.findViewById(R.id.tag_background_layout);
            content = itemView.findViewById(R.id.content);
        }
    }
}
