package com.echo.datatag3.util.business;

import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;

import com.echo.datatag3.bean.entity.ChatInfo;
import com.echo.datatag3.bean.enumeration.ChatType;
import com.echo.datatag3.interfaces.callback.chatinfo.ChatInfoCallback;
import com.echo.datatag3.util.database.ChatInfoDBUtil;

public final class ChatInfoUtil {

    public static void queryPrivateMessageUnreadNum(@NonNull ChatInfoCallback callback, long userId){
        new Thread(() -> {
            try {
                ChatInfo chatInfo = ChatInfoDBUtil.queryChatInfo(userId, ChatType.USER);
                callback.onSuccess(chatInfo);
            } catch (SQLiteException e) {
                callback.onSQLiteException();
            }
        }).start();
    }

    public static void refreshPrivateMessageUnreadNum(long userId, int unreadNum){
        new Thread(() -> {
            try {
                ChatInfo chatInfo = ChatInfoDBUtil.queryChatInfo(userId, ChatType.USER);
                if (chatInfo!=null){
                    chatInfo.setUnreadNum(unreadNum);
                    ChatInfoDBUtil.updateChatInfo(chatInfo);
                }
            } catch (SQLiteException e) {

            }
        }).start();
    }

}
