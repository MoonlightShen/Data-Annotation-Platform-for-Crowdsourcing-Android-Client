package com.echo.dapc.mvvm.model;

import androidx.recyclerview.widget.RecyclerView;

import com.echo.dapc.base.BaseModel;
import com.echo.dapc.base.adapter.BaseRecycleListAdapter;

public class ItemListModel<T, VH extends RecyclerView.ViewHolder> extends BaseModel {
    private BaseRecycleListAdapter<T, VH> adapter;

    private int lastClickPosition=-1;

    private int page=2;
    private int pageSize=10;

    public BaseRecycleListAdapter<T, VH> getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseRecycleListAdapter<T, VH> adapter) {
        this.adapter = adapter;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void addPage(){
        this.page++;
    }

    public void addPageSize(){
        if (pageSize<30){
            this.pageSize++;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getLastClickPosition() {
        return lastClickPosition;
    }

    public void setLastClickPosition(int lastClickPosition) {
        this.lastClickPosition = lastClickPosition;
    }
}
