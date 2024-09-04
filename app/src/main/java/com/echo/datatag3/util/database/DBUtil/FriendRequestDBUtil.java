package com.echo.datatag3.util.database.DBUtil;

import androidx.annotation.NonNull;

import com.echo.datatag3.MyApp;
import com.echo.datatag3.base.BaseDBUtil;
import com.echo.datatag3.bean.entity.FriendRequest;
import com.echo.datatag3.database.FriendRequestDao;

import org.greenrobot.greendao.Property;

public class FriendRequestDBUtil extends BaseDBUtil<FriendRequest,Void, FriendRequestDao> {
    @NonNull
    @Override
    protected FriendRequestDao getDao() {
        return MyApp.getDaoSession().getFriendRequestDao();
    }

    @NonNull
    @Override
    protected Property getPrimaryKeyProperty() {
        return null;
    }

    @Override
    protected long getPrimaryKey(FriendRequest friendRequest) {
        return 0;
    }
}
