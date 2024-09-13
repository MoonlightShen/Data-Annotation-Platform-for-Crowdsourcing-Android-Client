package com.echo.dapc.util.database;


import android.database.sqlite.SQLiteException;

import com.echo.dapc.MyApp;
import com.echo.dapc.bean.entity.PrivateMessage;
import com.echo.dapc.database.PrivateMessageDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public final class PrivateMessageDBUtil {
    private final static PrivateMessageDao dao;

    static {
        dao = MyApp.getDaoSession().getPrivateMessageDao();
    }

    public static PrivateMessage queryMessage(long id1, long id2) throws SQLiteException {
        QueryBuilder<PrivateMessage> qb = dao.queryBuilder();
        List<PrivateMessage> list = qb.where(
                        qb.or(
                                qb.and(PrivateMessageDao.Properties.ReceiverId.eq(id1), PrivateMessageDao.Properties.SenderId.eq(id2)),
                                qb.and(PrivateMessageDao.Properties.ReceiverId.eq(id2), PrivateMessageDao.Properties.SenderId.eq(id1))
                        )
                )
                .orderDesc(PrivateMessageDao.Properties.SendTime)
                .list();
        return list == null ? null : list.isEmpty() ? null : list.get(0);
    }

    public static Boolean existMessage(PrivateMessage privateMessage) throws SQLiteException {
        QueryBuilder<PrivateMessage> qb = dao.queryBuilder();
        List<PrivateMessage> list = qb.where(
                        PrivateMessageDao.Properties.MessageId.eq(privateMessage.getMessageId())
                )
                .list();
        return list != null && !list.isEmpty();
    }

    public static List<PrivateMessage> queryMessages(long id1, long id2, long startTime, int size) throws SQLiteException {
        QueryBuilder<PrivateMessage> qb = dao.queryBuilder();
        qb.limit(size);
        List<PrivateMessage> list = qb.where(
                        qb.or(
                                qb.and(PrivateMessageDao.Properties.ReceiverId.eq(id1), PrivateMessageDao.Properties.SenderId.eq(id2), PrivateMessageDao.Properties.SendTime.lt(startTime)),
                                qb.and(PrivateMessageDao.Properties.ReceiverId.eq(id2), PrivateMessageDao.Properties.SenderId.eq(id1), PrivateMessageDao.Properties.SendTime.lt(startTime))
                        )
                )
                .orderDesc(PrivateMessageDao.Properties.SendTime)
                .list();
        return list == null ? null : list.isEmpty() ? null : list;
    }

    public static void updateMessages(List<PrivateMessage> messages) throws SQLiteException{
        dao.updateInTx(messages);
    }

    public static void insertMessage(PrivateMessage message) throws SQLiteException{
        dao.insert(message);
    }

    public static void updateMessage(PrivateMessage message) throws SQLiteException{
        dao.update(message);
    }


}
