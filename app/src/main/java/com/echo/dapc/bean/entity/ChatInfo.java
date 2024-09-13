package com.echo.dapc.bean.entity;


import androidx.databinding.BaseObservable;

import com.echo.dapc.bean.converter.ChatTypeConverter;
import com.echo.dapc.bean.converter.ContentTypeConverter;
import com.echo.dapc.bean.enumeration.ChatType;
import com.echo.dapc.bean.enumeration.MessageContentType;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "dt_chat_info")
public class ChatInfo extends BaseObservable {
    private Long index;
    @Convert(converter = ChatTypeConverter.class, columnType = Integer.class)
    private ChatType chatType;
    @Convert(converter = ContentTypeConverter.class, columnType = Integer.class)
    private MessageContentType messageContentType;
    private String lastMessageContent;
    private Integer unreadNum;
    private Long lastMessageTime;



    @Generated(hash = 1365816708)
    public ChatInfo(Long index, ChatType chatType,
            MessageContentType messageContentType, String lastMessageContent,
            Integer unreadNum, Long lastMessageTime) {
        this.index = index;
        this.chatType = chatType;
        this.messageContentType = messageContentType;
        this.lastMessageContent = lastMessageContent;
        this.unreadNum = unreadNum;
        this.lastMessageTime = lastMessageTime;
    }

    @Generated(hash = 600262715)
    public ChatInfo() {
    }

    public Long getIndex() {
        return this.index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public ChatType getChatType() {
        return this.chatType;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public String getLastMessageContent() {
        return this.lastMessageContent;
    }

    public void setLastMessageContent(String lastMessageContent) {
        this.lastMessageContent = lastMessageContent;
    }

    public Integer getUnreadNum() {
        return this.unreadNum;
    }

    public void setUnreadNum(Integer unreadNum) {
        this.unreadNum = unreadNum;
    }

    public MessageContentType getContentType() {
        return messageContentType;
    }

    public void setContentType(MessageContentType messageContentType) {
        this.messageContentType = messageContentType;
    }

    public Long getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Long lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public MessageContentType getMessageContentType() {
        return this.messageContentType;
    }

    public void setMessageContentType(MessageContentType messageContentType) {
        this.messageContentType = messageContentType;
    }
}
