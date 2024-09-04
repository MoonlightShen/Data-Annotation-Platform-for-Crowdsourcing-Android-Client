package com.echo.datatag3.util.database;

import android.database.sqlite.SQLiteException;

import com.echo.datatag3.MyApp;
import com.echo.datatag3.bean.entity.ChatInfo;
import com.echo.datatag3.bean.enumeration.ChatType;
import com.echo.datatag3.database.ChatInfoDao;

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
