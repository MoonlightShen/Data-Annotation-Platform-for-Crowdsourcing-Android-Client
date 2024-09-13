package com.echo.dapc.bean.logic;

import com.echo.dapc.bean.entity.Team;
import com.echo.dapc.bean.enumeration.MessageContentType;
import com.echo.dapc.bean.enumeration.MessageWindowType;

public class MessageWindow {
    private Long messageWindowId;
    private String name;
    private Long lastSendTime;
    private String content;
    private MessageContentType messageContentType;
    private Integer unreadMessagesNum;
    private MessageWindowType type;

    public MessageWindow(Long messageWindowId, String name, Long lastSendTime, String content, MessageContentType messageContentType, Integer unreadMessagesNum, MessageWindowType type) {
        this.messageWindowId = messageWindowId;
        this.name = name;
        this.lastSendTime = lastSendTime;
        this.content = content;
        this.messageContentType = messageContentType;
        this.unreadMessagesNum = unreadMessagesNum;
        this.type = type;
    }

    public MessageWindow(Team team) {
//        this.messageWindowId = team.getTeamId();
//        this.name = team.getName();
//        this.lastMessage = team.getLastMessage();
//        this.lastMessageTime = team.getLastMessageTime();
//        this.type = MessageWindowType.TEAM;
    }

    public Integer getUnreadMessagesNum() {
        return unreadMessagesNum;
    }

    public void setUnreadMessagesNum(Integer unreadMessagesNum) {
        this.unreadMessagesNum = unreadMessagesNum;
    }

    public Long getMessageWindowId() {
        return messageWindowId;
    }

    public void setMessageWindowId(Long messageWindowId) {
        this.messageWindowId = messageWindowId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageWindowType getType() {
        return type;
    }

    public void setType(MessageWindowType type) {
        this.type = type;
    }

    public Long getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(Long lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageContentType getContentType() {
        return messageContentType;
    }

    public void setContentType(MessageContentType messageContentType) {
        this.messageContentType = messageContentType;
    }
}
