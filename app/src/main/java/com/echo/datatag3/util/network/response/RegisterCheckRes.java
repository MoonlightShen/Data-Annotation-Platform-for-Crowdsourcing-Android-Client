package com.echo.datatag3.util.network.response;

public class RegisterCheckRes {
    public String code;
    public Data data;

    public static class Data{
        public boolean exist;
    }
}
