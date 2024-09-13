package com.echo.dapc.mvvm.model;

import androidx.databinding.Bindable;

import com.echo.dapc.BR;
import com.echo.dapc.base.BaseModel;
import com.echo.dapc.base.ChatMessageListAdapter;

public final class ChatModel<T, Adapter extends ChatMessageListAdapter<T>> extends BaseModel {
    private Adapter adapter;
    private boolean moreMessages=true;

    private long chatObjectId;

    private boolean canSend;

    private int size = 0;

    @Bindable
    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    public boolean hasMoreMessages() {
        return moreMessages;
    }

    public void noMoreMessages() {
        this.moreMessages = false;
    }

    public long getChatObjectId() {
        return chatObjectId;
    }

    public void setChatObjectId(Long chatObjectId) {
        this.chatObjectId = chatObjectId;
    }

    @Bindable
    public boolean isCanSend() {
        return canSend;
    }

    public void setCanSend(boolean canSend) {
        this.canSend = canSend;
        notifyPropertyChanged(BR.canSend);
    }

    public int getSize() {
        if (size <= 50) size = Math.min(size + 5, 50);
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}


