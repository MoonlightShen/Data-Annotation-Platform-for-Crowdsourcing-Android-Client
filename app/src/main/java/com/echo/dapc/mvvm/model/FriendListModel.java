package com.echo.dapc.mvvm.model;

import androidx.databinding.Bindable;

import com.echo.dapc.BR;
import com.echo.dapc.adapter.MessageWindowListAdapter;
import com.echo.dapc.base.BaseModel;

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
