package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;
import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.bean.logic.DataFile;
import com.echo.datatag3.interfaces.callback.common.CommonEntityCallback;
import com.echo.datatag3.interfaces.callback.common.SQLiteDBDeleteCallback;
import com.echo.datatag3.interfaces.callback.common.SQLiteDBRefreshCallback;
import com.echo.datatag3.interfaces.callback.common.SQLiteDBUpdateCallback;
import com.echo.datatag3.mvvm.model.DataFileTextContentModel;
import com.echo.datatag3.mvvm.view.DataFileTextContentPage;
import com.echo.datatag3.util.GlobalConstant;
import com.echo.datatag3.util.business.DataFileUtil;
import com.echo.datatag3.util.kt.DialogUtil;
import com.echo.datatag3.util.system.CacheUtil;
import com.echo.datatag3.util.system.ToastUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class DataFileTextContentViewModel extends BaseViewModel<DataFileTextContentModel> {
    public DataFileTextContentViewModel(@NonNull Application application) {
        super(application);
    }

    public List<String> loadMoreData(int count) {
        List<String> list = new ArrayList<>();
        if ("\n".equals(getModel().getSeparator())) {
            String content;
            BufferedReader reader = getModel().getReader();
            try {
                while ((content = reader.readLine()) != null && list.size()<count) {
                    if (content.isEmpty()) continue;
                    list.add(content);
                }
                if (content==null){
                    getModel().getReader().close();
                }
            } catch (IOException e) {
                return null;
            }
        }
        return list;
    }

    public void localCachingDataFile(){
        if (!getModel().isLocalCached()&&getModel().getDatafile().getLocalCaching()){
            ToastUtil.toast("已经进行了本地缓存");
        }else {
            getModel().getDatafile().setLocalCaching(true);
            CacheUtil.cachingDataFile(dataFile -> {
                DataFileUtil.updateDataFile(() -> {
                    ToastUtil.success("缓存成功");
                    getModel().setLocalCached(true);
                }, dataFile);
            }, getModel().getDatafile());
        }
    }

    public void removeDataFile(){
        DialogUtil.createCustomDialog(getModel().getContext(), "您正在移除数据文件文件", "此操作不可恢复", "确定", "取消", new Function1<MaterialDialog, Unit>() {
            @Override
            public Unit invoke(MaterialDialog materialDialog) {
                DataFileUtil.removeLocalDataFile(new SQLiteDBDeleteCallback() {
                    @Override
                    public void onSuccess() {
                        ToastUtil.success("删除成功");
                        ((DataFileTextContentPage)getModel().getContext()).setResult(GlobalConstant.LIST_REMOVE_ITEM);
                        ((DataFileTextContentPage)getModel().getContext()).finish();
                    }

                    @Override
                    public void onSQLiteError() {
                        ToastUtil.toast("数据库异常");
                    }
                }, getModel().getDatafile().getDataFileIndex());
                return null;
            }
        }, null);
    }
}
