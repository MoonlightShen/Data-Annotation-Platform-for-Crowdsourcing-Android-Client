package com.echo.datatag3.util.database.DBUtil;

import androidx.annotation.NonNull;

import com.echo.datatag3.MyApp;
import com.echo.datatag3.base.BaseDBUtil;
import com.echo.datatag3.bean.entity.TeamNotice;
import com.echo.datatag3.database.TeamNoticeDao;

import org.greenrobot.greendao.Property;

public class TeamNoticeDBUtil extends BaseDBUtil<TeamNotice,Void, TeamNoticeDao> {
    @NonNull
    @Override
    protected TeamNoticeDao getDao() {
        return MyApp.getDaoSession().getTeamNoticeDao();
    }

    @NonNull
    @Override
    protected Property getPrimaryKeyProperty() {
        return TeamNoticeDao.Properties.NoticeId;
    }

    @Override
    protected long getPrimaryKey(TeamNotice teamNotice) {
        return teamNotice.getNoticeId();
    }
}
