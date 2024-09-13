package com.echo.dapc.util.network.request;

import android.annotation.SuppressLint;

import com.echo.dapc.base.BaseRequest;
import com.echo.dapc.bean.logic.Task;
import com.echo.dapc.util.network.constant.TaskConstant;
import com.echo.dapc.util.network.response.common.ExistRes;
import com.echo.dapc.util.network.response.common.StatusCodeRes;
import com.echo.dapc.util.network.response.task.TaskInfoRes;
import com.echo.dapc.util.network.response.task.TaskInfoListRes;
import com.echo.dapc.util.system.JsonUtil;

import java.io.IOException;

import okhttp3.Request;

public final class TaskRequest extends BaseRequest implements TaskConstant {

    public static TaskInfoListRes requestRecommendationTasks(int count) throws IOException {
        @SuppressLint("DefaultLocale") Request request = new Request.Builder()
                .url(RECOMMENDATION_TASK_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"count\": %d\r\n}", count)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getResponse(request).body().string(), TaskInfoListRes.class);
    }

    public static TaskInfoRes uploadTask(Task task) throws IOException {
        @SuppressLint("DefaultLocale") Request request = new Request.Builder()
                .url(UPLOAD_TASK_URL)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n  \"dataFiles\": %s,\r\n  \"title\": \"%s\",\r\n  \"description\": \"%s\",\r\n  " +
                                "\"startTime\": %s,\r\n  \"endTime\": %s,\r\n  \"inviteUsers\": %s,\r\n  \"tags\": %s,\r\n  \"groupSize\": %d,\r\n  \"visibleLevel\": %d,\r\n  " +
                                "\"totalQuota\": %d,\r\n  \"unitPoint\": %d,\r\n  \"taggingSceneType\": %d,\r\n  \"taggingSceneSettings\": \"%s\",\r\n  \"autoReviewAnswer\": %d,\r\n  " +
                                "\"autoApproveFetch\": %d\r\n}",
                        task.getDataFiles()==null?"[]":JsonUtil.toJson(task.getDataFiles()), task.getTitle(), task.getDescription(), task.getStartTime(),
                        task.getEndTime(), task.getInviteUsers()==null?"[]":JsonUtil.toJson(task.getInviteUsers()), task.getTags()==null?"[]":JsonUtil.toJson(task.getTags()),
                        task.getGroupSize(), task.getVisibleLevel()==null?1:task.getVisibleLevel().getIndex(), task.getTotalQuota(), task.getUnitPoint(),
                        task.getTaggingSceneType()==null?0:task.getTaggingSceneType().getIndex(), task.getTaggingSceneSettings()==null?"":JsonUtil.toJson(task.getTaggingSceneSettings()),
                        task.getAutoReviewAnswer()==null?0:task.getAutoReviewAnswer()?1:0, task.getAutoReviewAnswer()==null?0:task.getAutoReviewAnswer()?1:0)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getResponse(request).body().string(), TaskInfoRes.class);
    }

    public static StatusCodeRes likeTask(long taskId) throws IOException {
        @SuppressLint("DefaultLocale") Request request = new Request.Builder()
                .url(LIKE_TASK_PATH)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"taskId\": %s\r\n}", taskId)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getResponse(request).body().string(), StatusCodeRes.class);
    }

    public static StatusCodeRes cancelLikeTask(long taskId) throws IOException {
        @SuppressLint("DefaultLocale") Request request = new Request.Builder()
                .url(CANCEL_LIKE_TASK_PATH)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"taskId\": %s\r\n}", taskId)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getResponse(request).body().string(), StatusCodeRes.class);
    }

    public static ExistRes checkLikeTask(long taskId) throws IOException {
        @SuppressLint("DefaultLocale") Request request = new Request.Builder()
                .url(CHECK_LIKE_TASK_PATH)
                .method("POST", getRequestBody(MIME_JSON, String.format("{\r\n    \"taskId\": %s\r\n}", taskId)))
                .addHeader("token", getToken())
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Content-Type", CONTENT_JSON)
                .build();
        return JsonUtil.fromJson(getResponse(request).body().string(), ExistRes.class);
    }

}
