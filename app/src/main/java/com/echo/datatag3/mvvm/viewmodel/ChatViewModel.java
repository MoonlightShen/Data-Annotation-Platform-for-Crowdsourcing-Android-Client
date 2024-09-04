package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.base.ChatMessageListAdapter;
import com.echo.datatag3.mvvm.model.ChatModel;

public final class ChatViewModel<T, Adapter extends ChatMessageListAdapter<T>> extends BaseViewModel<ChatModel<T, Adapter>> {
    public ChatViewModel(@NonNull Application application) {
        super(application);
    }

}
