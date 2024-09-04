package com.echo.datatag3.util.network.response.task;

import java.util.List;
import java.util.Set;

public class TaskResponse {
    public Long taskId;
    public Long ownerId;
    public String title;
    public String description;
    public Long startTime;
    public Long endTime;
    public List<String> tags;
    public Integer state;
    public Integer likeNum;
    public Integer starNum;
    public Integer readNum;
    public Integer groupSize;
    public Integer visibleLevel;
    public Integer unitPoint;
    public Integer taggingSceneType;
    public String taggingSceneSettings;
    public List<Long> inviteUsers;
    public Integer totalQuota;
    public Integer remainingQuota;
    public List<Long> applyUsers;
    public Integer autoReviewAnswer;
    public Integer autoApproveFetch;
}
