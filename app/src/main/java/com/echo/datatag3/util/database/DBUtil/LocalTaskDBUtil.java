package com.echo.datatag3.util.database.DBUtil;

import androidx.annotation.NonNull;

import com.echo.datatag3.MyApp;
import com.echo.datatag3.base.BaseDBUtil;
import com.echo.datatag3.bean.entity.LocalTask;
import com.echo.datatag3.database.LocalTaskDao;

import org.greenrobot.greendao.Property;

public class LocalTaskDBUtil extends BaseDBUtil<LocalTask,Long, LocalTaskDao> {
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
