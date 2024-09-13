package com.echo.dapc.util.network.response;

public class LoginRes {
    public String code;
    public Data data;
    public static class Data{
        public Long userId;
        public Integer point;
        public String token;
    }
}
