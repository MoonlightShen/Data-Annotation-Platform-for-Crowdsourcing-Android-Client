package com.echo.dapc.util.network.request;

import com.echo.dapc.base.BaseRequest;
import com.echo.dapc.util.network.constant.DataFileConstant;

import java.io.IOException;
import java.util.Locale;

public class DataFileRequest extends BaseRequest implements DataFileConstant {
    public static void queryDataFiles() throws IOException {
        getJsonRequest(QUERY_DATA_FILE_URL, String.format(Locale.CHINA,"%d"));
    }
}
