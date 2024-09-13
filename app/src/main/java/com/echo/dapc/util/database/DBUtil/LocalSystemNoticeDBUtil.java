package com.echo.dapc.util.database.DBUtil;

import androidx.annotation.NonNull;

import com.echo.dapc.MyApp;
import com.echo.dapc.base.util.BaseLongKeyDBUtil;
import com.echo.dapc.bean.entity.LocalSystemNotice;
import com.echo.dapc.database.LocalSystemNoticeDao;

import org.greenrobot.greendao.Property;

public class LocalSystemNoticeDBUtil extends BaseLongKeyDBUtil<LocalSystemNotice, LocalSystemNoticeDao> {
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
