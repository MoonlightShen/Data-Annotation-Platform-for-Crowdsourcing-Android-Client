package com.echo.datatag3.util.network.request;

import com.echo.datatag3.base.BaseRequest;
import com.echo.datatag3.util.network.constant.LoadConstant;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class LoadRequest extends BaseRequest implements LoadConstant {

    public static void getLoginRes(String account, String password) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse(MIME_JSON), String.format("{\r\n    \"account\": \"%s\",\r\n    \"password\": \"%s\"\r\n}", account, password));
        Request request = new Request.Builder()
                .url(LOGIN_URL)
                .method("POST", body)
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        String response = getResponse(request).body().string();


    }

}
