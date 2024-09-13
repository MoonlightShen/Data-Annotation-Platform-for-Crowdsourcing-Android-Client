package com.echo.dapc.util.network.constant;

import com.echo.dapc.base.BaseConstant;

public interface ChatMessageConstant extends BaseConstant {
    String SEND_PRIVATE_MESSAGE_URL = NETWORK_ADDRESS + "/message/send/user";
    String QUERY_UNREAD_MESSAGE_INFO_URL = NETWORK_ADDRESS + "/message/unread";
    String QUERY_PRIVATE_MESSAGES_URL = NETWORK_ADDRESS + "/message/query/friend";
}
