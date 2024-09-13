package com.echo.dapc.util.business;

import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;

import com.echo.dapc.bean.entity.ChatInfo;
import com.echo.dapc.bean.enumeration.ChatType;
import com.echo.dapc.interfaces.callback.chatinfo.ChatInfoCallback;
import com.echo.dapc.util.database.ChatInfoDBUtil;

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
