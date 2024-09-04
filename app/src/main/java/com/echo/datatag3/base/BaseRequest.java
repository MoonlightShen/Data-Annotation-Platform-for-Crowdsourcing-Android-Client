package com.echo.datatag3.base;


import android.annotation.SuppressLint;

import com.echo.datatag3.MyApp;
import com.echo.datatag3.util.business.InfoUtil;
import com.echo.datatag3.util.network.response.task.TaskInfoListRes;
import com.echo.datatag3.util.system.JsonUtil;
import com.echo.datatag3.util.system.SharedSharedPreferencesUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class BaseRequest implements BaseConstant {
    protected static final OkHttpClient client;
    protected static final Gson gson;
    protected static final String MIME_JSON = "application/json";
    protected static final String MIME_UNKNOWN_BINARY_DATA_STREAM = "application/octet-stream";
    protected static final String CONTENT_JSON = "application/json";

    static {
        client = MyApp.getClient();
        gson = MyApp.getGson();
    }

    protected static String getToken() {
        String token = InfoUtil.getToken();
        return token==null?"":token;
    }

    protected static String getUserAgent() {
        //TODO 返回设备信息
        return "";
    }
    protected static RequestBody getRequestBody(String mimeType, String content){
        return RequestBody.create(MediaType.parse(mimeType), content);
    }

    protected static Response getResponse(Request request) throws IOException {
        return client.newCall(request).execute();
    }


    protected static String getStringResponse(Request request) throws IOException {
        return client.newCall(request).execute().body().string();
    }

    protected static Request getJsonRequest(String url,String postBody) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .method("POST", getRequestBody(MIME_TYPE.JSON.mimeValue, postBody))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return request;
    }
}
