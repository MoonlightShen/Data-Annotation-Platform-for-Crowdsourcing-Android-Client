package com.echo.dapc.util.business;

import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;

import com.echo.dapc.bean.entity.LocalDataFile;
import com.echo.dapc.bean.logic.DataFile;
import com.echo.dapc.interfaces.callback.common.CommonEntityCallback;
import com.echo.dapc.interfaces.callback.common.CommonInitCallback;
import com.echo.dapc.interfaces.callback.common.SQLiteDBDeleteCallback;
import com.echo.dapc.interfaces.callback.common.SQLiteDBQueryCallback;
import com.echo.dapc.interfaces.callback.common.SQLiteDBUpdateCallback;
import com.echo.dapc.util.database.DBUtilShop;
import com.echo.dapc.util.system.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

public final class DataFileUtil {

//    public static void queryLocalDataFile(CommonSQLiteDBQueryCallback<DataFile> callback, long dataFileIndex){
//        new Thread(() -> {
//            try{
//                LocalTask localTask = LocalTaskDBUtil.queryLocalTask(taskIndex);
//                callback.onSuccess(localTask);
//            }catch (SQLiteException e){
//                callback.onError(e.getMessage());
//            }
//        }).start();
//    }

    public static void initDataFiles(@NonNull CommonInitCallback<DataFile> callback) {
        ThreadUtil.runInBackground(()->{
            try {
                List<DataFile> dataFiles = new ArrayList<>();
                for (LocalDataFile localDataFile : DBUtilShop.localDataFileDBUtil.loadAllEntities()) {
                    dataFiles.add(DataFile.fromLocal(localDataFile));
                }
                //TODO 网络请求查询
                callback.onSuccess(dataFiles, true);
            } catch (SQLiteException e) {
                callback.onSQLiteDBError();
            }
        }, ThreadUtil.BackgroundStrategy.CACHED);
    }

    public static void queryLocalDataFiles(@NonNull SQLiteDBQueryCallback<DataFile> callback, int offset, int pageSize){
        ThreadUtil.submitToCached(()->{
            try {
                List<DataFile> dataFiles = new ArrayList<>();
                for (LocalDataFile localDataFile:DBUtilShop.localDataFileDBUtil.loadEntities(offset, pageSize)){
                    dataFiles.add(DataFile.fromLocal(localDataFile));
                }
                callback.onSuccess(dataFiles);
            }catch (SQLiteException e){
                callback.onSQLiteDBError();
            }
        });
    }

    public static void queryDataFile(CommonEntityCallback<DataFile> refreshCallback, DataFile dataFile) {
        ThreadUtil.runInBackground(()->{
            if (refreshCallback != null) {
                try {
                    refreshCallback.onSuccess(DataFile.fromLocal(DBUtilShop.localDataFileDBUtil.queryEntity(dataFile.getDataFileIndex())));
                } catch (SQLiteException e) {
                    refreshCallback.onSQLiteDBError();
                }
            }
        }, ThreadUtil.BackgroundStrategy.CACHED);
    }

    public static void updateDataFile(SQLiteDBUpdateCallback refreshCallback, DataFile dataFile) {
        ThreadUtil.runInBackground(()->{
            if (refreshCallback != null) {
                try {
                    LocalDataFile localDataFile = DataFile.toLocal(dataFile);
                    DBUtilShop.localDataFileDBUtil.refreshEntity(localDataFile);
                    refreshCallback.onSuccess();
                } catch (SQLiteException e) {
                    refreshCallback.onSQLiteDBError();
                }
            }
        }, ThreadUtil.BackgroundStrategy.CACHED);
    }

    public static void loadMoreDataFiles(@NonNull SQLiteDBQueryCallback<DataFile> callback, int offset, int pageSize) {
        ThreadUtil.submitToCached(() -> {
            try {
                List<LocalDataFile> localDataFileList = DBUtilShop.localDataFileDBUtil.loadEntities(offset, pageSize);
                if (localDataFileList!=null){
                    List<DataFile> dataFiles = new ArrayList<>();
                    for (LocalDataFile localDataFile : localDataFileList) {
                        dataFiles.add(DataFile.fromLocal(localDataFile));
                    }
                    callback.onSuccess(dataFiles);
                } else callback.onSuccess(null);
            } catch (SQLiteException e) {
                callback.onSQLiteDBError();
            }
        });
    }

    public static void queryLocalDataFile(@NonNull CommonEntityCallback<LocalDataFile> callback, long index) {
        ThreadUtil.submitToCached(() -> {
            try {
                callback.onSuccess(DBUtilShop.localDataFileDBUtil.queryEntity(index));
            } catch (SQLiteException e) {
                callback.onSQLiteDBError();
            }
        });
    }

    public static void queryDataFile(@NonNull CommonEntityCallback<DataFile> callback, long id) {
        ThreadUtil.submitToCached(() -> {
            try {
                //TODO 网络请求查询数据文件的信息
            } catch (SQLiteException e) {
                callback.onSQLiteDBError();
            }
        });
    }

    public static void removeLocalDataFile(@NonNull SQLiteDBDeleteCallback callback, long index) {
        ThreadUtil.submitToCached(() -> {
            try {
                LocalDataFile localDataFile = DBUtilShop.localDataFileDBUtil.queryEntity(index);
                if (localDataFile==null){
                    callback.onEntityNotExist();
                }else {
                    DBUtilShop.localDataFileDBUtil.deleteEntity(localDataFile);
                    callback.onSuccess();
                }
            } catch (SQLiteException e) {
                callback.onSQLiteError();
            }
        });
    }

}
