package com.echo.datatag3.util.database.DBUtil;

import androidx.annotation.NonNull;

import com.echo.datatag3.MyApp;
import com.echo.datatag3.base.BaseDBUtil;
import com.echo.datatag3.bean.entity.LocalDataFile;
import com.echo.datatag3.database.LocalDataFileDao;

import org.greenrobot.greendao.Property;

public class LocalDataFileDBUtil extends BaseDBUtil<LocalDataFile,Long, LocalDataFileDao> {

    @NonNull
    @Override
    protected LocalDataFileDao getDao() {
        return MyApp.getDaoSession().getLocalDataFileDao();
    }

    @NonNull
    @Override
    protected Property getPrimaryKeyProperty() {
        return LocalDataFileDao.Properties.LocalDataFileIndex;
    }

    @Override
    protected long getPrimaryKey(LocalDataFile localDataFile) {
        return localDataFile.getLocalDataFileIndex();
    }
}
