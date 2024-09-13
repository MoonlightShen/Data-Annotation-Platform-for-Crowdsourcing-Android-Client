package com.echo.dapc.util.database;

import android.database.sqlite.SQLiteException;

import com.echo.dapc.MyApp;
import com.echo.dapc.bean.entity.ChatInfo;
import com.echo.dapc.bean.enumeration.ChatType;
import com.echo.dapc.database.ChatInfoDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public final class ChatInfoDBUtil {
    private final static ChatInfoDao dao;

    static {
        dao = MyApp.getDaoSession().getChatInfoDao();
    }

    public static ChatInfo queryChatInfo(long index, ChatType type) throws SQLiteException {
        QueryBuilder<ChatInfo> qb = dao.queryBuilder();
        List<ChatInfo> list = qb.where(
                qb.and(ChatInfoDao.Properties.Index.eq(index), ChatInfoDao.Properties.ChatType.eq(type))
                )
                .list();
        return list == null ? null : list.isEmpty() ? null : list.get(0);
    }

    public static void updateChatInfo(ChatInfo chatInfo) throws SQLiteException {
        dao.updateInTx(chatInfo);
    }

}
