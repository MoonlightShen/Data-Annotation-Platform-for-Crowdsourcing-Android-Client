package com.echo.dapc.mvvm.model;

import androidx.databinding.Bindable;

import com.echo.dapc.BR;
import com.echo.dapc.adapter.MessageWindowListAdapter;
import com.echo.dapc.base.BaseModel;

public class TeamListModel extends BaseModel {
    private MessageWindowListAdapter adapter;

    @Bindable
    public MessageWindowListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(MessageWindowListAdapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }
}
