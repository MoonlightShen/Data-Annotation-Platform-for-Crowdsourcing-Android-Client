package com.echo.datatag3.util.network.response;

public class LoginRes {
    public String code;
    public Data data;
    public static class Data{
        public Long userId;
        public Integer point;
        public String token;
    }
}
