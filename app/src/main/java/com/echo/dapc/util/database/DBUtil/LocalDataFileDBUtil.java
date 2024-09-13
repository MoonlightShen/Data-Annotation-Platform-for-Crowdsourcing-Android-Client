package com.echo.dapc.util.database.DBUtil;

import androidx.annotation.NonNull;

import com.echo.dapc.MyApp;
import com.echo.dapc.base.util.BaseLongKeyDBUtil;
import com.echo.dapc.bean.entity.LocalDataFile;
import com.echo.dapc.database.LocalDataFileDao;

import org.greenrobot.greendao.Property;

public class LocalDataFileDBUtil extends BaseLongKeyDBUtil<LocalDataFile, LocalDataFileDao> {

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
