package com.echo.datatag3.util.network.constant;

import com.echo.datatag3.base.BaseConstant;

public interface TaskConstant extends BaseConstant {
    String RECOMMENDATION_TASK_URL = NETWORK_ADDRESS + "/task/query/random";
    String UPLOAD_TASK_URL = NETWORK_ADDRESS + "/task/insert";
    String LIKE_TASK_PATH = NETWORK_ADDRESS + "/task/like";
    String CANCEL_LIKE_TASK_PATH = NETWORK_ADDRESS + "/task/unlike";
    String CHECK_LIKE_TASK_PATH = NETWORK_ADDRESS + "/task/like/has";
}
