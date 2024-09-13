package com.echo.dapc.util.database.DBUtil;

import androidx.annotation.NonNull;

import com.echo.dapc.MyApp;
import com.echo.dapc.base.util.BaseNoKeyDBUtil;
import com.echo.dapc.bean.entity.TeamNotice;
import com.echo.dapc.database.TeamNoticeDao;

import org.greenrobot.greendao.Property;

public class TeamNoticeDBUtil extends BaseNoKeyDBUtil<TeamNotice, TeamNoticeDao> {
    @NonNull
    @Override
    protected TeamNoticeDao getDao() {
        return MyApp.getDaoSession().getTeamNoticeDao();
    }

    @Override
    protected long getPrimaryKey(TeamNotice teamNotice) {
        return teamNotice.getNoticeId();
    }
}
