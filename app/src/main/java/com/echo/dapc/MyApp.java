package com.echo.dapc;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.echo.dapc.database.DaoMaster;
import com.echo.dapc.database.DaoSession;
import com.echo.dapc.util.system.CacheUtil;
import com.google.gson.Gson;

import ando.file.core.FileOperator;
import okhttp3.OkHttpClient;

public class MyApp extends Application {
    private static final int DEFAULT_ALPHA = 200;
    private static Application context;
    private static DaoSession daoSession;
    private static SharedPreferences sharedPreferences;
    private static OkHttpClient client;
    private static Gson gson;

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static Application getMyAppContext() {
        return context;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static OkHttpClient getClient() {
        return client;
    }

    public static Gson getGson() {
        return gson;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        initGreenDao();
        initSharedPreferences();
        initOkHttpClient();
        initGson();

        initFontStyle();

        initCacheDirectory();

        FileOperator.INSTANCE.init(this, false);

    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "DataTag.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private void initSharedPreferences() {
        sharedPreferences = getSharedPreferences("app_data", Context.MODE_PRIVATE);
    }

    private void initOkHttpClient() {
        client = new OkHttpClient().newBuilder().build();
    }

    private void initGson() {
        gson = new Gson();
    }

    private void initFontStyle() {
//        ViewPump.init(ViewPump.builder()
//                .addInterceptor(new CalligraphyInterceptor(
//                        new CalligraphyConfig.Builder()
//                                .setDefaultFontPath("font/ali_puhui_regular.ttf")
//                                .setFontAttrId(io.github.inflationx.calligraphy3.R.attr.fontPath)
//                                .build()))
//                .build());
    }

    private void initCacheDirectory() {
        CacheUtil.initCache(context);
    }


}
