package com.echo.dapc.base.util;

import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;

import java.util.List;

public abstract class BaseLongKeyDBUtil<T, DAO extends AbstractDao<T, Long>> {
    private final DAO dao = getDao();

    protected abstract @NonNull DAO getDao();

    protected abstract @NonNull Property getPrimaryKeyProperty();

    protected abstract long getPrimaryKey(T t);

    public final T queryEntity(long entityPrimaryKey) throws SQLiteException {
        List<T> list = dao.queryBuilder().where(getPrimaryKeyProperty().eq(entityPrimaryKey)).list();
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    public final T queryEntity(T t) throws SQLiteException {
        List<T> list = dao.queryBuilder().where(getPrimaryKeyProperty().eq(getPrimaryKey(t))).list();
        return list == null || list.isEmpty() ? null : list.get(0);
    }

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

    public final long refreshEntity(T t) throws SQLiteException {
        if (existEntity(t)) {
            updateEntity(t);
            return getPrimaryKey(t);
        } else {
            return insertEntity(t);
        }
    }

    public final boolean existEntity(T t) {
        return queryEntity(t) != null;
    }

}
