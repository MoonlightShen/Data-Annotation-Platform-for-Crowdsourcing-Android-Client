package com.echo.datatag3.util.business;

import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;

import com.echo.datatag3.bean.entity.LocalSystemNotice;
import com.echo.datatag3.bean.enumeration.NoticeType;
import com.echo.datatag3.bean.logic.SystemNotice;
import com.echo.datatag3.interfaces.callback.common.SQLiteDBQueryCallback;
import com.echo.datatag3.util.database.DBUtilShop;
import com.echo.datatag3.util.system.ThreadUtil;
import com.echo.datatag3.util.system.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class SystemNoticeUtil {

    @Deprecated
    public static List<SystemNotice> getExampleSystemNotices(){
        List<SystemNotice> systemNotices = new ArrayList<>();
        Random random =new Random();
        long currentTime = TimeUtil.getCurrentTime();
        systemNotices.add(new SystemNotice(null, null, (long)Math.abs(random.nextInt(90))+10,
                NoticeType.CUSTOM, "自定义消息标题" , "自定义消息内容", false, currentTime+(currentTime%10000+Math.abs(random.nextInt(100000)))*Math.abs(random.nextInt(10000))+Math.abs(random.nextInt(1000000000))));
        systemNotices.add(new SystemNotice(null, null, (long)Math.abs(random.nextInt(90))+10,
                NoticeType.TASK_REQUEST_PENDING_APPROVAL, "" , "", false, currentTime+(currentTime%10000+Math.abs(random.nextInt(100000)))*Math.abs(random.nextInt(10000))+Math.abs(random.nextInt(1000000000))));
        systemNotices.add(new SystemNotice(null, null, (long)Math.abs(random.nextInt(90))+10,
                NoticeType.TASK_REQUEST_APPLY, "" , "", false, currentTime+(currentTime%10000+Math.abs(random.nextInt(100000)))*Math.abs(random.nextInt(10000))+Math.abs(random.nextInt(1000000000))));
        return systemNotices;
    }

    public static void loadSystemNotice(@NonNull SQLiteDBQueryCallback<SystemNotice> callback, int offset, int pageSize) {
        ThreadUtil.submitToCached(() -> {
            try {
                List<SystemNotice> systemNotices = new ArrayList<>();
//                for (LocalSystemNotice localSystemNotice : DBUtilShop.localSystemNoticeDBUtil.loadEntities(offset, pageSize)) {
//                    systemNotices.add(new SystemNotice().fromLocal(localSystemNotice));
//                }
                systemNotices.addAll(getExampleSystemNotices());
                callback.onSuccess(systemNotices);
            } catch (SQLiteException e) {
                callback.onSQLiteDBError();
            }
        });
    }

    public static void querySystemNotice(@NonNull SQLiteDBQueryCallback<SystemNotice> callback, long localSystemNoticeIndex) {
        ThreadUtil.submitToCached(() -> {
            try {
                List<SystemNotice> systemNotices = new ArrayList<>();
                LocalSystemNotice localSystemNotice = DBUtilShop.localSystemNoticeDBUtil.queryEntity(localSystemNoticeIndex);
                if (localSystemNotice != null)
                    systemNotices.add(new SystemNotice().fromLocal(localSystemNotice));
                callback.onSuccess(systemNotices);
            } catch (SQLiteException e) {
                callback.onSQLiteDBError();
            }
        });
    }

}
