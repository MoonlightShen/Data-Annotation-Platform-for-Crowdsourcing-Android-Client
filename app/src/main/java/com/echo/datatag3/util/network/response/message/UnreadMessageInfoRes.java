package com.echo.datatag3.util.network.response.message;

import java.util.Map;

public class UnreadMessageInfoRes {
    public String code;
    public Data data;

    public static class Data{
        public Map<String, FriendMessageInfo> friendMessage;
        public Map<String, TeamMessageInfo> teamMessage;

        public static class FriendMessageInfo{
            public Integer total;
            public Integer contentType;
            public String content;
            public Long lastSendTime;
        }
        public static class TeamMessageInfo{
            public Integer total;
            public Integer contentType;
            public String content;
            public Long lastSendTime;
            public Long lastSender;
        }
    }

}
