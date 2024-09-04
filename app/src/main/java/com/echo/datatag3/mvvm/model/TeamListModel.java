package com.echo.datatag3.mvvm.model;

import androidx.databinding.Bindable;

import com.echo.datatag3.BR;
import com.echo.datatag3.adapter.MessageWindowListAdapter;
import com.echo.datatag3.base.BaseModel;

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
