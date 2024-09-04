package com.echo.datatag3.util.database;

import com.echo.datatag3.util.database.DBUtil.FriendRequestDBUtil;
import com.echo.datatag3.util.database.DBUtil.LocalDataFileDBUtil;
import com.echo.datatag3.util.database.DBUtil.LocalSystemNoticeDBUtil;
import com.echo.datatag3.util.database.DBUtil.LocalTaskDBUtil;
import com.echo.datatag3.util.database.DBUtil.TeamNoticeDBUtil;
import com.echo.datatag3.util.database.DBUtil.UntaggingDataDBUtil;
import com.echo.datatag3.util.database.DBUtil.UntaggingDataSetDBUtil;

public final class DBUtilShop {
    public static final LocalDataFileDBUtil localDataFileDBUtil = new LocalDataFileDBUtil();
    public static final LocalTaskDBUtil localTaskDBUtil = new LocalTaskDBUtil();
    public static final TeamNoticeDBUtil teamNoticeDBUtil = new TeamNoticeDBUtil();
    public static final FriendRequestDBUtil friendRequestDBUtil = new FriendRequestDBUtil();
    public static final LocalSystemNoticeDBUtil localSystemNoticeDBUtil = new LocalSystemNoticeDBUtil();
    public static final UntaggingDataDBUtil untaggingDataDBUtil = new UntaggingDataDBUtil();
    public static final UntaggingDataSetDBUtil untaggingDatasetDBUtil = new UntaggingDataSetDBUtil();
}
