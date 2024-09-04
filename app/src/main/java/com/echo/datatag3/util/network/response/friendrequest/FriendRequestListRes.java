package com.echo.datatag3.util.network.response.friendrequest;

import java.util.List;

public class FriendRequestListRes {
    public String code;
    public List<Data> data;

    public static class Data{
        public long requestId;
        public long userId;
        public long requesterId;
        public String validateContent;
        public int validationStatus;
        public long requestTime;
    }
}
