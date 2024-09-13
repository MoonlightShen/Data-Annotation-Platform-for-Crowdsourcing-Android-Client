package com.echo.dapc.util.business;

import android.database.sqlite.SQLiteException;

import com.echo.dapc.bean.entity.TeamNotice;
import com.echo.dapc.interfaces.callback.teamnotice.LoadAllTeamNoticesCallback;
import com.echo.dapc.util.database.DBUtilShop;

import java.util.List;

public final class TeamNoticeUtil {

    public static void loadAllTeamNotices(LoadAllTeamNoticesCallback callback){
        new Thread(()-> {
            try {
                List<TeamNotice> notices = DBUtilShop.teamNoticeDBUtil.loadAllEntities();
                //TODO 加上网络查询到的好友请求
                callback.onSuccess(notices);
            }catch (SQLiteException e){
                callback.onError(e.getMessage());
            }
        }).start();
    }

}
