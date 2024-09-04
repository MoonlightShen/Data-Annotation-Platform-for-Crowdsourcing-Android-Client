package com.echo.datatag3.util.business;

import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;

import com.echo.datatag3.bean.entity.ChatInfo;
import com.echo.datatag3.bean.entity.PrivateMessage;
import com.echo.datatag3.bean.enumeration.ChatType;
import com.echo.datatag3.bean.enumeration.MessageContentType;
import com.echo.datatag3.bean.enumeration.StatusCode;
import com.echo.datatag3.interfaces.callback.chatinfo.ChatInfoListCallback;
import com.echo.datatag3.interfaces.callback.message.PrivateMessageCallback;
import com.echo.datatag3.interfaces.callback.message.PrivateMessageListCallback;
import com.echo.datatag3.interfaces.callback.message.SendMessageCallback;
import com.echo.datatag3.util.database.ChatInfoDBUtil;
import com.echo.datatag3.util.database.PrivateMessageDBUtil;
import com.echo.datatag3.util.network.request.ChatMessageRequest;
import com.echo.datatag3.util.network.response.message.MessageInfoListRes;
import com.echo.datatag3.util.network.response.message.MessageInfoRes;
import com.echo.datatag3.util.network.response.message.UnreadMessageInfoRes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ChatMessageUtil {

    public static void queryUnreadInfo(@NonNull ChatInfoListCallback callback) {
        new Thread(() -> {
            try {
                UnreadMessageInfoRes res = ChatMessageRequest.queryUnreadInfo();
                if (StatusCode.success(res.code)) {
                    for (Map.Entry<String, UnreadMessageInfoRes.Data.FriendMessageInfo> entry : res.data.friendMessage.entrySet()) {
                        try {
                            long id = Long.parseLong(entry.getKey());
                            ChatInfo chatInfo = ChatInfoDBUtil.queryChatInfo(id, ChatType.USER);
                            if (chatInfo != null) {
                                chatInfo.setUnreadNum(entry.getValue().total);
                                chatInfo.setLastMessageContent(entry.getValue().content);
                                chatInfo.setContentType(MessageContentType.getByIndex(entry.getValue().contentType));
                                chatInfo.setLastMessageTime(entry.getValue().lastSendTime);
                                ChatInfoDBUtil.updateChatInfo(chatInfo);
                            }
                        } catch (NumberFormatException | SQLiteException ignored) {
                            //TODO 标记日志
                        }
                    }
                    callback.onSuccess(res.data);
                } else {
                    callback.unknownError(res.code);
                }
            } catch (IOException e) {
                callback.onNoNetwork();
            }
        }).start();
    }

    public static void queryPrivateMessages(@NonNull PrivateMessageListCallback callback, long friendId, long myId, long startTime, int size) {
        new Thread(() -> {
            try {
                List<PrivateMessage> messages = PrivateMessageDBUtil.queryMessages(friendId, myId, startTime, size);
                if (messages == null || messages.size() < size) {
                    MessageInfoListRes res = ChatMessageRequest.queryPrivateMessages(friendId, startTime, messages == null ? size : size - messages.size());
                    if (StatusCode.success(res.code)) {
                        if (messages == null) messages = new ArrayList<>();
                        for (MessageInfoListRes.Data data : res.data) {
                            messages.add(new PrivateMessage(data.messageId, data.senderId, data.receiverId,
                                    data.content, MessageContentType.getByIndex(data.contentType), data.sendTime));
                        }
                        for (PrivateMessage privateMessage:messages){
                            if (PrivateMessageDBUtil.existMessage(privateMessage)){
                                PrivateMessageDBUtil.updateMessage(privateMessage);
                            }else {
                                PrivateMessageDBUtil.insertMessage(privateMessage);
                            }
                        }
                        callback.onSuccess(messages, messages.size()>=size);
                    } else {
                        callback.unknownError(res.code);
                    }
                } else {
                    callback.onSuccess(messages, true);
                }
            } catch (SQLiteException e) {
                callback.onSQLiteException();
            } catch (IOException e) {
                callback.noNetwork();
            }
        }).start();
    }

    public static void queryPrivateMessage(@NonNull PrivateMessageCallback callback, long id1, long id2) {
        new Thread(() -> {
            try {
                PrivateMessage message = PrivateMessageDBUtil.queryMessage(id1, id2);
                callback.onSuccess(message);
            } catch (SQLiteException e) {
                callback.onSQLiteException();
            }
        }).start();
    }

    public static void sendPrivateChat(@NonNull SendMessageCallback callback, long receiverId, MessageContentType messageContentType, String content, String filePath) {
        new Thread(() -> {
            try {
                MessageInfoRes res = ChatMessageRequest.sendPrivateMessage(receiverId, messageContentType, content, filePath == null ? null : new File(filePath));
                if (StatusCode.success(res.code)) {
                    callback.onSuccess(res.data);
                } else {
                    callback.unknownError(res.code);
                }
            } catch (IOException e) {

            }
        }).start();
    }

}
