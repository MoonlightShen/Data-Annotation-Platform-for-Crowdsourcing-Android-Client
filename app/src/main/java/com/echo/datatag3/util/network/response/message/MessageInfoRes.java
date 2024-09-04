package com.echo.datatag3.util.network.response.message;

public class MessageInfoRes {
    public String code;
    public Data data;
    public static class Data{
        public Long messageId;
        public Long senderId;
        public Long receiverId;
        public Long teamId;
        public String content;
        public int messageContentType;
        public Long sendTime;
    }
}
