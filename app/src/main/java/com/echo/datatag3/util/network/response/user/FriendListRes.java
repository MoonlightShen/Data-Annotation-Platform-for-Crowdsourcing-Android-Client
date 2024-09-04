package com.echo.datatag3.util.network.response.user;

import java.util.List;

public class FriendListRes {
    public String code;
    public List<Data> data;
    public static class Data{
        public Long userId;
        public String remark;
        public String nickname;
    }
}
