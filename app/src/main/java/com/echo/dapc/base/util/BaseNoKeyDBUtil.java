package com.echo.dapc.base.util;

import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;

public abstract class BaseNoKeyDBUtil<T, DAO extends AbstractDao<T, Void>> {
    private final DAO dao = getDao();

    protected abstract @NonNull DAO getDao();

    protected abstract long getPrimaryKey(T t);

    public final List<T> loadEntities(int offset, int pageSize) throws SQLiteException {
        return dao.queryBuilder().offset(offset).limit(pageSize).list();
    }

    public final long insertEntity(T t) throws SQLiteException {
        return dao.insert(t);
    }

    public final void updateEntity(T t) throws SQLiteException {
        dao.update(t);
    }

    public final void deleteEntity(T t) throws SQLiteException {
        dao.delete(t);
    }

    public final List<T> loadAllEntities() throws SQLiteException {
        return dao.loadAll();
    }

}
