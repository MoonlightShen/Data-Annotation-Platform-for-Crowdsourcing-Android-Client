package com.echo.datatag3.util.database.DBUtil;

import androidx.annotation.NonNull;

import com.echo.datatag3.MyApp;
import com.echo.datatag3.base.BaseDBUtil;
import com.echo.datatag3.bean.entity.LocalSystemNotice;
import com.echo.datatag3.database.LocalSystemNoticeDao;

import org.greenrobot.greendao.Property;

public class LocalSystemNoticeDBUtil extends BaseDBUtil<LocalSystemNotice, Long, LocalSystemNoticeDao> {
    @NonNull
    @Override
    protected LocalSystemNoticeDao getDao() {
        return MyApp.getDaoSession().getLocalSystemNoticeDao();
    }

    @NonNull
    @Override
    protected Property getPrimaryKeyProperty() {
        return LocalSystemNoticeDao.Properties.NoticeIndex;
    }

    @Override
    protected long getPrimaryKey(LocalSystemNotice localSystemNotice) {
        return localSystemNotice.getNoticeIndex();
    }
}
