package com.echo.datatag3.util.network.response.message;

import java.util.List;

public class MessageInfoListRes {
    public String code;
    public List<Data> data;
    public static class Data {
        public Long messageId;
        public Long senderId;
        public int contentType;
        public String content;
        public Long sendTime;
        public Long receiverId;
        public Long teamId;
    }
}
