package com.echo.datatag3.util.network.request;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseRequest;
import com.echo.datatag3.bean.enumeration.Gender;
import com.echo.datatag3.util.network.constant.UserConstant;
import com.echo.datatag3.util.network.response.LoginRes;
import com.echo.datatag3.util.network.response.RegisterCheckRes;
import com.echo.datatag3.util.network.response.common.StatusCodeRes;
import com.echo.datatag3.util.network.response.friendrequest.FriendRequestListRes;
import com.echo.datatag3.util.network.response.user.FriendListRes;
import com.echo.datatag3.util.network.response.user.UserInfoRes;
import com.echo.datatag3.util.network.response.user.UserListRes;
import com.echo.datatag3.util.network.response.user.UserNicknameRes;
import com.echo.datatag3.util.system.JsonUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UserRequest extends BaseRequest implements UserConstant {

    public static boolean getAccountCheckRes(@NonNull String account) throws IOException {
        Request request = new Request.Builder()
                .url(CHECK_ACCOUNT_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"account\": \"%s\"\r\n}", account)))
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        RegisterCheckRes check = JsonUtil.fromJson(getResponse(request).body().string(), RegisterCheckRes.class);
        return check.data.exist;
    }

    public static boolean getPhoneCheckRes(@NonNull String phone) throws IOException {
        Request request = new Request.Builder()
                .url(CHECK_PHONE_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"phone\": \"%s\"\r\n}", phone)))
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        RegisterCheckRes check = JsonUtil.fromJson(getResponse(request).body().string(), RegisterCheckRes.class);
        return check.data.exist;
    }

    public static boolean getEmailCheckRes(@NonNull String email) throws IOException {
        Request request = new Request.Builder()
                .url(CHECK_EMAIL_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"email\": \"%s\"\r\n}", email)))
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        RegisterCheckRes check = JsonUtil.fromJson(getResponse(request).body().string(), RegisterCheckRes.class);
        return check.data.exist;
    }

    public static StatusCodeRes getRegisterRes(@NonNull String account, @NonNull String phone, @NonNull String email, @NonNull String password) throws IOException {
        Request request = new Request.Builder()
                .url(REGISTER_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"account\": \"%s\",\r\n    \"phone\": \"%s\",\r\n    \"email\": \"%s\",\r\n    \"password\": \"%s\"\r\n}", account, phone, email, password)))
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getResponse(request).body().string(), StatusCodeRes.class);
    }

    public static LoginRes login(@NonNull String account, @NonNull String phone, @NonNull String email, @NonNull  String password) throws IOException {
        Request request = new Request.Builder()
                .url(LOGIN_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"account\": \"%s\",\r\n    \"phone\": \"%s\",\r\n    \"email\": \"%s\",\r\n    \"password\": \"%s\"\r\n}", account, phone, email, password)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getStringResponse(request), LoginRes.class);
    }

    public static StatusCodeRes logout() throws IOException {
        Request request = new Request.Builder()
                .url(LOGOUT_URL)
                .method("POST", getRequestBody(MIME_JSON, ""))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getStringResponse(request), StatusCodeRes.class);
    }

    public static UserNicknameRes queryUserNickname(long userId) throws IOException {
        Request request = new Request.Builder()
                .url(QUERY_USER_NICKNAME_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"userId\": %s\r\n}", userId)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getStringResponse(request), UserNicknameRes.class);
    }

    public static UserInfoRes queryUserInfo(long userId) throws IOException{
        Request request = new Request.Builder()
                .url(QUERY_USER_INFO_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"userId\": %s\r\n}", userId)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getStringResponse(request), UserInfoRes.class);
    }

    public static StatusCodeRes updateUserInfo(@NonNull String nickname, @NonNull Gender gender, @NonNull Integer age, @NonNull String  introduction) throws IOException{
        @SuppressLint("DefaultLocale") Request request = new Request.Builder()
                .url(UPDATE_USER_INFO_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"nickname\": \"%s\",\r\n    \"gender\": %d,\r\n    \"age\": %d,\r\n    \"introduction\": \"%s\"\r\n}",
                        nickname, gender.getIndex(), age, introduction)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getStringResponse(request), StatusCodeRes.class);
    }

    public static StatusCodeRes updateUseAvatar(@NonNull File file) throws IOException{
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file",file.getName(),
                        RequestBody.create(MediaType.parse(MIME_UNKNOWN_BINARY_DATA_STREAM),
                                file))
                .build();
        Request request = new Request.Builder()
                .url(UPDATE_USER_AVATAR_URL)
                .method("POST", body)
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getStringResponse(request), StatusCodeRes.class);
    }

    public static UserListRes queryUsers(@NonNull String nickname) throws IOException{
        Request request = new Request.Builder()
                .url(QUERY_USER_BY_NICKNAME_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"nickname\": \"%s\"\r\n}", nickname)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getStringResponse(request), UserListRes.class);
    }

    public static StatusCodeRes postFriendRequest( long userId, @NonNull String validateContent)throws IOException{
        Request request = new Request.Builder()
                .url(CREATE_FRIEND_REQUEST_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"userId\": %s,\r\n    \"validateContent\": \"%s\"\r\n}", userId, validateContent)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getStringResponse(request), StatusCodeRes.class);
    }

    public static FriendRequestListRes getFriendRequestToMe() throws IOException {
        Request request = new Request.Builder()
                .url(QUERY_FRIEND_REQUEST_TO_ME_URL)
                .method("POST", getRequestBody(MIME_JSON, ""))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getStringResponse(request), FriendRequestListRes.class);
    }

    public static FriendRequestListRes getMyFriendRequest() throws IOException {
        Request request = new Request.Builder()
                .url(QUERY_MY_FRIEND_REQUEST_URL)
                .method("POST", getRequestBody(MIME_JSON, ""))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getStringResponse(request), FriendRequestListRes.class);
    }

    public static StatusCodeRes refuseFriendRequest(long requestId, @NonNull String reply) throws IOException {
        Request request = new Request.Builder()
                .url(FRIEND_REQUEST_REFUSE_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"requestId\": %s,\r\n    \"reply\": \"%s\"\r\n}",
                        requestId , reply)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getStringResponse(request), StatusCodeRes.class);
    }

    public static StatusCodeRes ignoreFriendRequest(long requestId) throws IOException {
        Request request = new Request.Builder()
                .url(FRIEND_REQUEST_IGNORE_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"requestId\": %s\r\n}",
                        requestId)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getStringResponse(request), StatusCodeRes.class);
    }

    public static StatusCodeRes agreeFriendRequest(long requestId, @NonNull String remark) throws IOException {
        Request request = new Request.Builder()
                .url(FRIEND_REQUEST_AGREE_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"requestId\": %s,\r\n    \"friendNote\": \"%s\"\r\n}",
                        requestId , remark)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getStringResponse(request), StatusCodeRes.class);
    }

    public static FriendListRes loadAllFriends()throws IOException {
        Request request = new Request.Builder()
                .url(LOAD_FRIENDS_URL)
                .method("POST", getRequestBody(MIME_JSON, ""))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getStringResponse(request), FriendListRes.class);

    }
}
