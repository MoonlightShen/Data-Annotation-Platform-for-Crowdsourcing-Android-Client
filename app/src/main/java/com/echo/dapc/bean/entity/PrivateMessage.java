package com.echo.dapc.bean.entity;

import androidx.databinding.BaseObservable;

import com.echo.dapc.bean.converter.ContentTypeConverter;
import com.echo.dapc.bean.enumeration.MessageContentType;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "dt_private_messages")
public class PrivateMessage extends BaseObservable {
    @Id(autoincrement = true)
    @Unique
    private Long messageId;
    private Long senderId;
    private Long receiverId;
    private String content;
    @Convert(converter = ContentTypeConverter.class, columnType = Integer.class)
    private MessageContentType messageContentType;
    private Long sendTime;


    @Generated(hash = 1976540434)
    public PrivateMessage(Long messageId, Long senderId, Long receiverId,
            String content, MessageContentType messageContentType, Long sendTime) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.messageContentType = messageContentType;
        this.sendTime = sendTime;
    }

    @Generated(hash = 1511861578)
    public PrivateMessage() {
    }


    public Long getMessageId() {
        return this.messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getSenderId() {
        return this.senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return this.receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageContentType getContentType() {
        return this.messageContentType;
    }

    public void setContentType(MessageContentType messageContentType) {
        this.messageContentType = messageContentType;
    }

    public Long getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public MessageContentType getMessageContentType() {
        return this.messageContentType;
    }

    public void setMessageContentType(MessageContentType messageContentType) {
        this.messageContentType = messageContentType;
    }
}
