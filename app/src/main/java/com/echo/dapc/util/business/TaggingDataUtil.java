package com.echo.dapc.util.business;

import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;

import com.echo.dapc.bean.entity.UntaggingData;
import com.echo.dapc.bean.entity.UntaggingDataSet;
import com.echo.dapc.interfaces.callback.untaggingdata.SaveTaggingProgressCallback;
import com.echo.dapc.util.database.DBUtilShop;
import com.echo.dapc.util.system.ThreadUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TaggingDataUtil {
    public static void saveTaggingProgress(@NonNull SaveTaggingProgressCallback callback, @NonNull List<UntaggingData> untaggingDataList, @NonNull Set<Integer> taggedPositions ,long untaggingDataSetIndex, int currentDataIndex, @NonNull Map<Integer, String> optionDict){
        ThreadUtil.runInBackground(() -> {
            UntaggingDataSet set = DBUtilShop.untaggingDatasetDBUtil.queryEntity(untaggingDataSetIndex);
            if (set!=null){
                if (currentDataIndex<0||currentDataIndex>=untaggingDataList.size()){
                    callback.currentDataIndexOutOfRange();
                }else {
                    try {
                        for (Integer position:taggedPositions){
                            DBUtilShop.untaggingDataDBUtil.refreshEntity(untaggingDataList.get(position));
                        }
                    }catch (SQLiteException e){
                        callback.onSaveDataError();
                        return;
                    }
                    set.setLastTagging(currentDataIndex);
                    set.setTaggingOptionDict(optionDict);
                    try {
                        DBUtilShop.untaggingDatasetDBUtil.refreshEntity(set);
                        callback.onSuccess();
                    }catch (SQLiteException e){
                        callback.onSaveSetError();
                    }
                }
            }else {
                callback.setNotExist();
            }
        }, ThreadUtil.BackgroundStrategy.CACHED);
    }
}
