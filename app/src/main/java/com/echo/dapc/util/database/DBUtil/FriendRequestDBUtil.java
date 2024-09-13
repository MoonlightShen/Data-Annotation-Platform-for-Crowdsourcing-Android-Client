package com.echo.dapc.util.database.DBUtil;

import androidx.annotation.NonNull;

import com.echo.dapc.MyApp;
import com.echo.dapc.base.util.BaseNoKeyDBUtil;
import com.echo.dapc.bean.entity.FriendRequest;
import com.echo.dapc.database.FriendRequestDao;

import org.greenrobot.greendao.Property;

public class FriendRequestDBUtil extends BaseNoKeyDBUtil<FriendRequest, FriendRequestDao> {
    @NonNull
    @Override
    protected FriendRequestDao getDao() {
        return MyApp.getDaoSession().getFriendRequestDao();
    }

    @Override
    protected long getPrimaryKey(FriendRequest friendRequest) {
        return 0;
    }
}
