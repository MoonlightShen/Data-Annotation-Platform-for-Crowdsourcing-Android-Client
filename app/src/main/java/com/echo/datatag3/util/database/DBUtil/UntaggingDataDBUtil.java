package com.echo.datatag3.util.database.DBUtil;

import androidx.annotation.NonNull;

import com.echo.datatag3.MyApp;
import com.echo.datatag3.base.BaseDBUtil;
import com.echo.datatag3.bean.entity.UntaggingData;
import com.echo.datatag3.database.UntaggingDataDao;

import org.greenrobot.greendao.Property;

import java.util.ArrayList;
import java.util.List;

public class UntaggingDataDBUtil extends BaseDBUtil<UntaggingData, Long, UntaggingDataDao> {
    @NonNull
    @Override
    protected UntaggingDataDao getDao() {
        return MyApp.getDaoSession().getUntaggingDataDao();
    }

    @NonNull
    @Override
    protected Property getPrimaryKeyProperty() {
        return UntaggingDataDao.Properties.DataIndex;
    }

    @Override
    protected long getPrimaryKey(UntaggingData untaggingData) {
        return untaggingData.getDataId();
    }

    public List<UntaggingData> queryUntaggingDataBySetId(long setId){
        return getDao().queryBuilder().where(UntaggingDataDao.Properties.UntaggingDataSetId.eq(setId)).list();
    }
}
