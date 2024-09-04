package com.echo.datatag3.util.network.request;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseRequest;
import com.echo.datatag3.interfaces.callback.user.RequestAvatarCallback;
import com.echo.datatag3.util.network.constant.AvatarConstant;

import java.io.IOException;

import okhttp3.Request;

public class AvatarRequest extends BaseRequest implements AvatarConstant {

    public static void requestUserAvatar(@NonNull RequestAvatarCallback callback, long userId) throws IOException {
        Request request = new Request.Builder()
                .url(USER_AVATAR_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"userId\": %s\r\n}", userId)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        byte[] response = client.newCall(request).execute().body().bytes();
        callback.onSuccess(response);
    }

    public static void requestTeamAvatar(@NonNull RequestAvatarCallback callback, long teamId) throws IOException {
        Request request = new Request.Builder()
                .url(TEAM_AVATAR_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"teamId\": %s\r\n}", teamId)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        byte[] response = client.newCall(request).execute().body().bytes();
        callback.onSuccess(response);
    }

}
