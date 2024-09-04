package com.echo.datatag3.util.business;

import android.database.sqlite.SQLiteException;

import com.echo.datatag3.bean.entity.TeamNotice;
import com.echo.datatag3.interfaces.callback.teamnotice.LoadAllTeamNoticesCallback;
import com.echo.datatag3.util.database.DBUtilShop;

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
