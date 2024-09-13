package com.echo.dapc.util.database.DBUtil;

import androidx.annotation.NonNull;

import com.echo.dapc.MyApp;
import com.echo.dapc.base.util.BaseLongKeyDBUtil;
import com.echo.dapc.bean.entity.UntaggingDataSet;
import com.echo.dapc.database.UntaggingDataSetDao;

import org.greenrobot.greendao.Property;

public class UntaggingDataSetDBUtil extends BaseLongKeyDBUtil<UntaggingDataSet, UntaggingDataSetDao> {
    @NonNull
    @Override
    protected UntaggingDataSetDao getDao() {
        return MyApp.getDaoSession().getUntaggingDataSetDao();
    }

    @NonNull
    @Override
    protected Property getPrimaryKeyProperty() {
        return UntaggingDataSetDao.Properties.SetIndex;
    }

    @Override
    protected long getPrimaryKey(UntaggingDataSet untaggingDataSet) {
        return untaggingDataSet.getSetIndex();
    }
}
