package com.echo.datatag3.base;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.datatag3.R;
import com.echo.datatag3.util.system.ThreadUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseSelectableListAdapter extends BaseRecycleViewAdapter<BaseSelectableListAdapter.Option, BaseSelectableListAdapter.ViewHolder> {

    private final Set<Integer> multiSelectItems = new HashSet<>();
    private boolean multipleSelect = false;
    private boolean cancelable = true;
    private int selectPosition = -1;

    private SelectCallback mSelectCallback;

    public BaseSelectableListAdapter() {
        this(new ArrayList<>());
    }

    public void setSelectCallback(SelectCallback mSelectCallback) {
        this.mSelectCallback = mSelectCallback;
    }

    public BaseSelectableListAdapter(List<String> data) {
        super(new ArrayList<>());
        List<Option> options = new ArrayList<>();
        for (String s:data){
            options.add(new Option(options.size(), s));
        }
        setData(options);
        init();
    }

    private void init(){
        setClickListener((itemview, position) -> {
            select(position);
        });
    }

    @Override
    protected int getViewLayoutId(int viewType) {
        return R.layout.option_not_selected;
    }

    @NonNull
    @Override
    protected BaseSelectableListAdapter.ViewHolder createViewHolder(@NonNull View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindViewHolder(@NonNull Option option, @NonNull ViewHolder viewHolder) {
        viewHolder.content.setText(option.content);
        if (isSelect(option.position)){
            viewHolder.background.setCardBackgroundColor(option.selectedBackgroundColor);
            viewHolder.content.setTextColor(option.selectedTextColor);
        }else {
            viewHolder.background.setCardBackgroundColor(option.unselectedBackgroundColor);
            viewHolder.content.setTextColor(option.unselectedTextColor);
        }
    }

    public boolean isSelect(int position){
        return multipleSelect&&multiSelectItems.contains(position)||!multipleSelect&&selectPosition==position;
    }

    public void clearSelect() {
        if (multipleSelect) {
            for (Integer select : new HashSet<>(multiSelectItems)) {
                select(select);
            }
            multiSelectItems.clear();
        } else {
            select(selectPosition);
            selectPosition = -1;
        }
    }

    public void select(int position) {
        if (multipleSelect) multiSelect(position);
        else singleSelect(position);
        if (mSelectCallback!=null){
            mSelectCallback.select(position);
        }
    }

    private void multiSelect(int position) {
        if (multipleSelect) {
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
            notifyItemChanged(position);
        } else {
            int lastSelect = selectPosition;
            selectPosition = position;
            if (lastSelect!=-1){
                notifyItemChanged(lastSelect);
            }
            notifyItemChanged(position);
        }
    }

    public List<Integer> getSelectIndex() {
        if (multipleSelect) {
            return new ArrayList<>(multiSelectItems);
        } else {
            return new ArrayList<>(selectPosition);
        }
    }

    public boolean isMultipleSelect() {
        return multipleSelect;
    }

    public void setMultipleSelectMode(boolean multipleSelect) {
        this.multipleSelect = multipleSelect;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public static final class Option {
        public final int position;
        public final String content;
        public final @ColorInt int unselectedBackgroundColor;
        public final @ColorInt int unselectedTextColor;
        public final @ColorInt int selectedBackgroundColor;
        public final @ColorInt int selectedTextColor;

        public Option(int position, String content) {
            this(position, content, Color.parseColor("#F6F6F6"), Color.parseColor("#858585"), Color.parseColor("#C9F2FD"), Color.parseColor("#3A71A9"));
        }

        public Option(int position,String content, int unselectedBackgroundColor, int unselectedTextColor, int selectedBackgroundColor, int selectedTextColor) {
            this.position = position;
            this.content = content;
            this.unselectedBackgroundColor = unselectedBackgroundColor;
            this.unselectedTextColor = unselectedTextColor;
            this.selectedBackgroundColor = selectedBackgroundColor;
            this.selectedTextColor = selectedTextColor;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView background;
        public final TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.background);
            content = itemView.findViewById(R.id.content);
        }
    }

    public interface SelectCallback{
        void select(int position);
    }
}
