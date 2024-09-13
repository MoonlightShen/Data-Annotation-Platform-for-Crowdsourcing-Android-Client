package com.echo.dapc.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.base.ChatMessageListAdapter;
import com.echo.dapc.mvvm.model.ChatModel;

public final class ChatViewModel<T, Adapter extends ChatMessageListAdapter<T>> extends BaseViewModel<ChatModel<T, Adapter>> {
    public ChatViewModel(@NonNull Application application) {
        super(application);
    }

}
