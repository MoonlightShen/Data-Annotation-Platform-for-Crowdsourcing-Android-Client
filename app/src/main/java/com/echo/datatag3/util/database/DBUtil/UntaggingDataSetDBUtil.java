package com.echo.datatag3.util.database.DBUtil;

import androidx.annotation.NonNull;

import com.echo.datatag3.MyApp;
import com.echo.datatag3.base.BaseDBUtil;
import com.echo.datatag3.bean.entity.UntaggingData;
import com.echo.datatag3.bean.entity.UntaggingDataSet;
import com.echo.datatag3.database.UntaggingDataDao;
import com.echo.datatag3.database.UntaggingDataSetDao;

import org.greenrobot.greendao.Property;

import java.util.ArrayList;
import java.util.List;

public class UntaggingDataSetDBUtil extends BaseDBUtil<UntaggingDataSet, Long, UntaggingDataSetDao> {
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
