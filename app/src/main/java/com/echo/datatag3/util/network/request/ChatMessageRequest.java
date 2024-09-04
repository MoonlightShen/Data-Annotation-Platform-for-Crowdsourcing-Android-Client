package com.echo.datatag3.util.network.request;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseRequest;
import com.echo.datatag3.bean.enumeration.MessageContentType;
import com.echo.datatag3.util.network.constant.ChatMessageConstant;
import com.echo.datatag3.util.network.response.message.MessageInfoListRes;
import com.echo.datatag3.util.network.response.message.MessageInfoRes;
import com.echo.datatag3.util.network.response.message.UnreadMessageInfoRes;
import com.echo.datatag3.util.system.JsonUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public final class ChatMessageRequest extends BaseRequest implements ChatMessageConstant {

    public static MessageInfoRes sendPrivateMessage(long receiverId, @NonNull MessageContentType messageContentType, String content, File file) throws IOException {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("receiverId",String.valueOf(receiverId))
                .addFormDataPart("contentType",String.valueOf(messageContentType.getIndex()))
                .addFormDataPart("content",content==null?"":content);
        if (file!=null&& messageContentType == MessageContentType.FILE){
            builder.addFormDataPart("file","",
                    RequestBody.create(MediaType.parse(MIME_UNKNOWN_BINARY_DATA_STREAM),
                            file));
        }
        Request request = new Request.Builder()
                .url(SEND_PRIVATE_MESSAGE_URL)
                .method("POST", builder.build())
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .build();
        return JsonUtil.fromJson(getStringResponse(request), MessageInfoRes.class);
    }

    public static UnreadMessageInfoRes queryUnreadInfo() throws IOException{
        Request request = new Request.Builder()
                .url(QUERY_UNREAD_MESSAGE_INFO_URL)
                .method("POST", getRequestBody(MIME_JSON, ""))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getStringResponse(request), UnreadMessageInfoRes.class);
    }

    public static MessageInfoListRes queryPrivateMessages(long friendId, long startTime, int size) throws IOException{
        @SuppressLint("DefaultLocale") Request request = new Request.Builder()
                .url(QUERY_PRIVATE_MESSAGES_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"friendId\": %s,\r\n    \"time\": %s,\r\n    \"count\": %d\r\n}",
                       friendId, startTime, size)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getStringResponse(request), MessageInfoListRes.class);
    }

}
