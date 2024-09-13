package com.echo.dapc.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;
import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.interfaces.callback.common.SQLiteDBDeleteCallback;
import com.echo.dapc.mvvm.model.DataFileTextContentModel;
import com.echo.dapc.mvvm.view.DataFileTextContentActivity;
import com.echo.dapc.util.GlobalConstant;
import com.echo.dapc.util.business.DataFileUtil;
import com.echo.dapc.util.kt.DialogUtil;
import com.echo.dapc.util.system.CacheUtil;
import com.echo.dapc.util.system.ToastUtil;

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
            ToastUtil.normal("已经进行了本地缓存");
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
                        ((DataFileTextContentActivity)getModel().getContext()).setResult(GlobalConstant.LIST_REMOVE_ITEM);
                        ((DataFileTextContentActivity)getModel().getContext()).finish();
                    }

                    @Override
                    public void onSQLiteError() {
                        ToastUtil.normal("数据库异常");
                    }
                }, getModel().getDatafile().getDataFileIndex());
                return null;
            }
        }, null);
    }
}
