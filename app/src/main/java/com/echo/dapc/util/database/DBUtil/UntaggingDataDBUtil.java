package com.echo.dapc.util.database.DBUtil;

import androidx.annotation.NonNull;

import com.echo.dapc.MyApp;
import com.echo.dapc.base.util.BaseLongKeyDBUtil;
import com.echo.dapc.bean.entity.UntaggingData;
import com.echo.dapc.database.UntaggingDataDao;

import org.greenrobot.greendao.Property;

import java.util.List;

public class UntaggingDataDBUtil extends BaseLongKeyDBUtil<UntaggingData, UntaggingDataDao> {
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
