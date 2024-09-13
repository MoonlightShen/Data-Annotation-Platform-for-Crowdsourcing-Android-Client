package com.echo.dapc.util.database.DBUtil;

import androidx.annotation.NonNull;

import com.echo.dapc.MyApp;
import com.echo.dapc.base.util.BaseLongKeyDBUtil;
import com.echo.dapc.bean.entity.LocalTask;
import com.echo.dapc.database.LocalTaskDao;

import org.greenrobot.greendao.Property;

public class LocalTaskDBUtil extends BaseLongKeyDBUtil<LocalTask, LocalTaskDao> {
    @NonNull
    @Override
    protected LocalTaskDao getDao() {
        return MyApp.getDaoSession().getLocalTaskDao();
    }

    @NonNull
    @Override
    protected Property getPrimaryKeyProperty() {
        return LocalTaskDao.Properties.TaskIndex;
    }

    @Override
    protected long getPrimaryKey(LocalTask localTask) {
        return localTask.getTaskIndex();
    }
}
