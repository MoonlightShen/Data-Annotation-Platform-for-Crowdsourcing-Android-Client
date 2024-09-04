package com.echo.datatag3.util.system;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ThreadUtil {
    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private static final int SCHEDULED_CORE_POOL_SIZE = 2;
    private static ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(SCHEDULED_CORE_POOL_SIZE);

    public static boolean isMainThread(){
        return Looper.myLooper()==Looper.getMainLooper();
    }

    public enum BackgroundStrategy{
        CACHED;
    }

    public static void runOnUiThread(@NonNull final Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            MAIN_HANDLER.post(runnable);
        }
    }
    public static void runInBackground(@NonNull final Runnable runnable, @NonNull final BackgroundStrategy strategy){
        if (isMainThread()){
            if (strategy==BackgroundStrategy.CACHED){
                cachedThreadPool.submit(runnable);
            }
        }else {
            runnable.run();
        }
    }

    public static void submitToCached(@NonNull final Runnable runnable){
        if (!cachedThreadPool.isShutdown()){
            cachedThreadPool.submit(runnable);
        }else {
            cachedThreadPool = Executors.newCachedThreadPool();
        }
    }

    public static void submitToScheduled(@NonNull final Runnable runnable){
        if (!scheduledThreadPool.isShutdown()){
            scheduledThreadPool.submit(runnable);
        }else {
            scheduledThreadPool = Executors.newScheduledThreadPool(SCHEDULED_CORE_POOL_SIZE);
        }
    }

}
