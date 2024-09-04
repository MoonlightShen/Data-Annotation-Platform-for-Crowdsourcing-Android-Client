package com.echo.datatag3.util.network.response.user;

public class UserInfoRes {
    public String code;
    public Data data;
    public static class Data{
        public String nickname;
        public Integer gender;
        public Integer age;
        public String phone;
        public String introduction;
        public String email;
    }
}
