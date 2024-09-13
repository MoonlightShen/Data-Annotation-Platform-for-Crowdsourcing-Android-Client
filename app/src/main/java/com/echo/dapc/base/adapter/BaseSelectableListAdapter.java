package com.echo.dapc.base.adapter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseSelectableListAdapter<T, OP extends BaseSelectableOption, VH extends RecyclerView.ViewHolder> extends BaseRecycleListAdapter<OP, VH> {

    private final Set<Integer> multiSelectItems = new HashSet<>();
    private boolean multipleSelect = false;
    private boolean cancelable = true;
    private int selectPosition = -1;
    private SelectCallback selectCallback;

    public BaseSelectableListAdapter() {
        this(new ArrayList<>());
    }

    public BaseSelectableListAdapter(List<T> data) {
        super(new ArrayList<>());
        List<OP> options = new ArrayList<>();
        for (T t : data) {
            options.add(createOption(t));
        }
        setData(options);
        init();
    }

    protected abstract OP createOption(T data);

    private void init() {
        setClickListener((itemview, position) -> {
            selectCallback.beforeSelect(position);
            select(position);
            selectCallback.afterSelect(position);
        });
    }

    public boolean isSelect(int position) {
        return multipleSelect && multiSelectItems.contains(position) || !multipleSelect && selectPosition == position;
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
            if (lastSelect != -1) {
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

    public void setSelectCallback(SelectCallback selectCallback) {
        this.selectCallback = selectCallback;
    }

    public interface SelectCallback {
        void beforeSelect(int position);

        void afterSelect(int position);
    }
}
