package com.echo.datatag3.util.network.constant;

import com.echo.datatag3.base.BaseConstant;

public interface UserConstant extends BaseConstant {
    String CHECK_ACCOUNT_URL = NETWORK_ADDRESS + "/user/exist/account";
    String CHECK_PHONE_URL = NETWORK_ADDRESS + "/user/exist/phone";
    String CHECK_EMAIL_URL = NETWORK_ADDRESS + "/user/exist/email";
    String REGISTER_URL = NETWORK_ADDRESS + "/user/register";
    String LOGIN_URL = NETWORK_ADDRESS + "/user/login";
    String LOGOUT_URL = NETWORK_ADDRESS + "/user/logout";
    String QUERY_USER_NICKNAME_URL = NETWORK_ADDRESS + "/user/query/id/user1";
    String QUERY_USER_INFO_URL = NETWORK_ADDRESS + "/user/query/id/user3";
    String UPDATE_USER_INFO_URL = NETWORK_ADDRESS + "/user/update/info";
    String UPDATE_USER_AVATAR_URL = NETWORK_ADDRESS + "/user/update/avatar";
    String QUERY_USER_BY_NICKNAME_URL = NETWORK_ADDRESS + "/user/query/nickname";
    String CREATE_FRIEND_REQUEST_URL = NETWORK_ADDRESS + "/friend/add/friend";
    String QUERY_FRIEND_REQUEST_TO_ME_URL = NETWORK_ADDRESS + "/friend/query/friend/request1";
    String QUERY_MY_FRIEND_REQUEST_URL = NETWORK_ADDRESS + "/friend/query/friend/request2";

    String FRIEND_REQUEST_REFUSE_URL = NETWORK_ADDRESS + "/friend/refuse/request";
    String FRIEND_REQUEST_IGNORE_URL = NETWORK_ADDRESS + "/friend/ignore/request";
    String FRIEND_REQUEST_AGREE_URL = NETWORK_ADDRESS + "/friend/agree/request";

    String LOAD_FRIENDS_URL = NETWORK_ADDRESS + "/friend/query/friend";
}
