package com.echo.dapc.util.network.response.friendrequest;

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
