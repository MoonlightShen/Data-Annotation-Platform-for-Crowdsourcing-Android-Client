package com.echo.datatag3.mvvm.view;

import androidx.annotation.NonNull;

import com.echo.datatag3.adapter.PrivateMessageListAdapter;
import com.echo.datatag3.base.BaseChatActivity;
import com.echo.datatag3.bean.entity.PrivateMessage;
import com.echo.datatag3.bean.enumeration.MessageContentType;
import com.echo.datatag3.interfaces.callback.message.PrivateMessageListCallback;
import com.echo.datatag3.interfaces.callback.message.SendMessageCallback;
import com.echo.datatag3.util.business.ChatMessageUtil;
import com.echo.datatag3.util.business.InfoUtil;
import com.echo.datatag3.util.network.response.message.MessageInfoRes;
import com.echo.datatag3.util.system.TimeUtil;
import com.echo.datatag3.util.system.ToastUtil;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

public class PrivateChatView extends BaseChatActivity<PrivateMessage, PrivateMessageListAdapter> {

    @NonNull
    @Override
    protected String getChatObjectIdKey() {
        return "friend_id";
    }

    @NonNull
    @Override
    protected String getTitleText() {
        return getStringExtra("friend_name");
    }

    @NonNull
    @Override
    protected PrivateMessageListAdapter createAdapter() {
        return new PrivateMessageListAdapter();
    }

    @NonNull
    @Override
    protected OnRefreshListener createRefreshListener() {
        return refreshLayout -> {
            PrivateMessage earliestMessage = getEarliestMessage();
            if (!getModel().hasMoreMessages()) {
                refreshLayout.finishRefresh();
                ToastUtil.toast("没有更多消息了");
            } else {
                ChatMessageUtil.queryPrivateMessages(new PrivateMessageListCallback() {
                    @Override
                    public void onSuccess(List<PrivateMessage> messages, boolean hasMore) {
                        runOnUiThread(()->{
                            refreshLayout.finishRefresh();
                            getModel().getAdapter().addItems(messages);
                        });
                        if (!hasMore) {
                            getModel().noMoreMessages();
                        }
                    }

                    @Override
                    public void onSQLiteException() {
                        ToastUtil.error("数据库异常");
                    }

                    @Override
                    public void unknownError(String errorCode) {
                        ToastUtil.info("未知错误" + errorCode);
                    }

                    @Override
                    public void noNetwork() {

                    }
                }, getChatObjectId(), InfoUtil.getUserId(), earliestMessage==null?TimeUtil.getCurrentTime():earliestMessage.getSendTime()-1, getModel().getSize());
            }
        };
    }

    @NonNull
    @Override
    protected SendTextMessageCallback createTextSendMessageCallback() {
        return messageContent -> {
            ChatMessageUtil.sendPrivateChat(new SendMessageCallback() {
                @Override
                public void onSuccess(MessageInfoRes.Data data) {
                    addMessage(new PrivateMessage(data.messageId, InfoUtil.getUserId(), getChatObjectId(), messageContent, MessageContentType.TEXT, TimeUtil.getCurrentTime()));
                    scrollToBottom();
                }

                @Override
                public void unknownError(String errorCode) {
                    ToastUtil.error("未知错误"+errorCode);
                }
            }, getChatObjectId(), MessageContentType.TEXT, messageContent, null);
        };
    }

    @Override
    protected void initPage() {
        ChatMessageUtil.queryPrivateMessages(new PrivateMessageListCallback() {
            @Override
            public void onSuccess(List<PrivateMessage> messages, boolean hasMore) {
                runOnUiThread(()->{
                    getModel().getAdapter().refreshItems(messages);
                });
                if (!hasMore) {
                    getModel().noMoreMessages();
                }
            }

            @Override
            public void onSQLiteException() {
                ToastUtil.error("数据库异常");
            }

            @Override
            public void unknownError(String errorCode) {
                ToastUtil.info("未知错误" + errorCode);
            }

            @Override
            public void noNetwork() {

            }
        }, getChatObjectId(), InfoUtil.getUserId(), TimeUtil.getCurrentTime(), getModel().getSize());
    }
}
