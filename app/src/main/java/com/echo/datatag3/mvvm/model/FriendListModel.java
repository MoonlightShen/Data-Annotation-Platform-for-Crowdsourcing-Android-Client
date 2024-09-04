package com.echo.datatag3.mvvm.model;

import androidx.databinding.Bindable;

import com.echo.datatag3.BR;
import com.echo.datatag3.adapter.MessageWindowListAdapter;
import com.echo.datatag3.base.BaseModel;

public class FriendListModel extends BaseModel {
    private MessageWindowListAdapter adapter;
    private Runnable queryUnreadMessageInfo;
    private boolean runnableActivate;

    @Bindable
    public MessageWindowListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(MessageWindowListAdapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    public Runnable getQueryUnreadMessageInfo() {
        return queryUnreadMessageInfo;
    }

    public void setQueryUnreadMessageInfo(Runnable queryUnreadMessageInfo) {
        this.queryUnreadMessageInfo = queryUnreadMessageInfo;
    }

    public boolean isRunnableActivate() {
        return runnableActivate;
    }

    public void setRunnableActivate(boolean runnableActivate) {
        this.runnableActivate = runnableActivate;
    }
}
