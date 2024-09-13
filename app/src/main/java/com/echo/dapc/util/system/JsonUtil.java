package com.echo.dapc.util.system;

import com.echo.dapc.MyApp;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

public final class JsonUtil {

    private static final Gson gson;

    static {
        gson = MyApp.getGson();
    }

    public static Gson getGson() {
        return gson;
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }

    public static <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

}
