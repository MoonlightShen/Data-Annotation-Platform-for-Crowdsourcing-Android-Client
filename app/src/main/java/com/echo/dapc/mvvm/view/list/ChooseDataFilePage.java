package com.echo.dapc.mvvm.view.list;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.echo.dapc.adapter.DataFileListAdapter;
import com.echo.dapc.adapter.viewholder.DataFileViewHolder;
import com.echo.dapc.base.BaseItemListChooseActivity;
import com.echo.dapc.base.adapter.BaseRecycleListAdapter;
import com.echo.dapc.bean.logic.DataFile;
import com.echo.dapc.interfaces.callback.common.CommonInitCallback;
import com.echo.dapc.interfaces.callback.common.SQLiteDBQueryCallback;
import com.echo.dapc.util.business.DataFileUtil;
import com.echo.dapc.util.system.JsonUtil;
import com.echo.dapc.util.system.ToastUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseDataFilePage extends BaseItemListChooseActivity<DataFile, DataFileViewHolder> {

    @NonNull
    @Override
    protected String getTitleString() {
        return "选择数据文件";
    }

    @NonNull
    @Override
    protected BaseRecycleListAdapter<DataFile, DataFileViewHolder> createAdapter() {
        return new DataFileListAdapter();
    }

    @NonNull
    @Override
    protected OnTitleBarListener getTitleBarListener() {
        return new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }
        };
    }

    @Override
    protected void initLoading() {
        DataFileUtil.initDataFiles(new CommonInitCallback<>() {
            @Override
            public void onSuccess(List<DataFile> list, boolean moreInServer) {
                showItems(list, moreInServer);
            }

            @Override
            public void noNetWork() {
                showNoNetwork();
            }

            @Override
            public void onSQLiteDBError() {
                showError();
                ToastUtil.normal("数据库异常");
            }

            @Override
            public void onMangoDBError() {
                showError();
                ToastUtil.normal("数据库异常");
            }
        });
    }

    @NonNull
    @Override
    protected OnLoadMoreListener createOnLoadMoreListener() {
        return refreshLayout -> {
            int pageSize = getModel().getPageSize();
            DataFileUtil.loadMoreDataFiles(new SQLiteDBQueryCallback<>() {
                @Override
                public void onSuccess(List<DataFile> list) {
                    refreshLayout.finishLoadMore();
                    addItems(list, getItemCount(), list != null && list.size() == pageSize);
                }

                @Override
                public void onSQLiteDBError() {
                    refreshLayout.finishLoadMore();
                    showError();
                }
            }, getItemCount(), pageSize);
        };
    }

    @Override
    protected BaseRecycleListAdapter.OnItemClickListener createOnItemClickListener() {
        return (itemview, position) -> {
            DataFile dataFile = getItem(position);
            if (dataFile.getDataFileId() != null) {
                Intent intent = new Intent();
                Map<Long, Integer> map = new HashMap<>();
                map.put(dataFile.getDataFileId(), dataFile.getDataNum());
                intent.putExtra("uploaded_datafile_id", JsonUtil.toJson(map));
                setResult(RESULT_OK, intent);
                finish();
            }else if (dataFile.getDataFileIndex()!=null){
                Intent intent = new Intent();
                Map<Long, Integer> map = new HashMap<>();
                map.put(dataFile.getDataFileIndex(), dataFile.getDataNum());
                intent.putExtra("local_datafile_index", JsonUtil.toJson(map));
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }
}