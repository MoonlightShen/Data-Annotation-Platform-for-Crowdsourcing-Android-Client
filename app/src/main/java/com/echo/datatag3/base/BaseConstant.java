package com.echo.datatag3.base;

import com.echo.datatag3.util.system.SharedSharedPreferencesUtil;

public interface BaseConstant {
    String NETWORK_ADDRESS = "http://10.100.79.114:3888";

    enum MIME_TYPE{
        JSON("application/json"),
        UNKNOWN_BINARY_DATA_STREAM("application/octet-stream");

        final String mimeValue;

        MIME_TYPE(String mimeValue) {
            this.mimeValue = mimeValue;
        }
    }
}
