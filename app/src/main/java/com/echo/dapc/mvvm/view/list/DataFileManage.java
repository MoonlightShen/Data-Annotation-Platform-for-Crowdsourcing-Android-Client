package com.echo.dapc.mvvm.view.list;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.echo.dapc.R;
import com.echo.dapc.adapter.DataFileListAdapter;
import com.echo.dapc.adapter.viewholder.DataFileViewHolder;
import com.echo.dapc.base.activity.BaseActivity;
import com.echo.dapc.base.activity.BaseItemListActivity;
import com.echo.dapc.base.adapter.BaseRecycleListAdapter;
import com.echo.dapc.bean.base.FileInfo;
import com.echo.dapc.bean.enumeration.DataType;
import com.echo.dapc.bean.enumeration.FileType;
import com.echo.dapc.bean.logic.DataFile;
import com.echo.dapc.interfaces.callback.common.CommonEntityCallback;
import com.echo.dapc.interfaces.callback.common.SQLiteDBQueryCallback;
import com.echo.dapc.mvvm.view.DataFileTextContentActivity;
import com.echo.dapc.util.business.DataFileUtil;
import com.echo.dapc.util.business.InfoUtil;
import com.echo.dapc.util.kt.DialogUtil;
import com.echo.dapc.util.system.CacheUtil;
import com.echo.dapc.util.system.FileUtil;
import com.echo.dapc.util.system.TimeUtil;
import com.echo.dapc.util.system.ToastUtil;
import com.echo.dapc.widget.bottomdialog.ImportFilesDialog;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataFileManage extends BaseItemListActivity<DataFile, DataFileViewHolder> {
    private ImportFilesDialog dialog;

    @NonNull
    @Override
    protected String getTitleString() {
        return "数据文件管理";
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

            @Override
            public void onRightClick(TitleBar titleBar) {
                dialog.show();
            }
        };
    }

    private final Set<String> hasImportedPaths = new HashSet<>();

    @NonNull
    @Override
    protected OnRefreshLoadMoreListener createRefreshLoadMoreListener() {
        return new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                int pageSize = getModel().getPageSize();
                DataFileUtil.loadMoreDataFiles(new SQLiteDBQueryCallback<>() {
                    @Override
                    public void onSuccess(List<DataFile> list) {
                        refreshLayout.finishLoadMore();
                        addItems(list, 0, list != null && list.size() == pageSize);
                    }

                    @Override
                    public void onSQLiteDBError() {
                        refreshLayout.finishLoadMore();
                        showError();
                    }
                }, getItemCount(), pageSize);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                DataFile dataFile = getLastClickEntity();
                if (dataFile != null) {
                    DataFileUtil.queryDataFile(dataFile.getDataFileIndex() != null ? new CommonEntityCallback<>() {
                        @Override
                        public void onSuccess(DataFile dataFile) {
                            refreshLayout.finishRefresh();
                            refreshItem(dataFile, getLastClickPosition());
                            ToastUtil.success("刷新成功");
                        }

                        @Override
                        public void onSQLiteDBError() {
                            refreshLayout.finishRefresh();
                            showError();
                            ToastUtil.error("数据库异常");
                        }
                    } : null, dataFile);
                } else {
                    refreshLayout.finishRefresh();
                }
            }
        };
    }

    @Override
    protected void initLoading() {
        int pageSize = getModel().getPageSize();
        DataFileUtil.loadMoreDataFiles(new SQLiteDBQueryCallback<>() {
            @Override
            public void onSuccess(List<DataFile> list) {
                if (list != null && !list.isEmpty()) Collections.reverse(list);
                showItems(list, list != null && list.size() == pageSize);
            }

            @Override
            public void onSQLiteDBError() {
                showError();
            }
        }, getItemCount(), pageSize);
    }

    @Override
    protected void initActivity() {
        super.initActivity();
        getBinding().listTbTitle.setRightIcon(R.drawable.common_upload_file_gray);
        dialog = new ImportFilesDialog(getSupportFragmentManager(), new ImportFilesDialog.Callback() {
            @Override
            public void onConfirm(List<String> paths) {
                if (hasImportedPaths.isEmpty()) {
                    for (DataFile dataFile : getItems()) {
                        hasImportedPaths.add(dataFile.getFileInfo().getPath());
                    }
                }
                List<CharSequence> duplicate = new ArrayList<>();
                List<Integer> toImport = new ArrayList<>();
                int sum = 0;
                for (String path : paths) {
                    if (hasImportedPaths.contains(path)) {
                        duplicate.add(path);
                    } else {
                        toImport.add(sum++);
                        DataFile dataFile = new DataFile(null, null, InfoUtil.getUserId(), "\n",
                                null, TimeUtil.getCurrentTime(),
                                dialog.isLocalCaching(), null, null, null, null, dialog.getFileTag(), new FileInfo(path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf(".")),
                                path,
                                FileType.getBySuffix(path.substring(path.lastIndexOf(".") + 1)),
                                null, null,
                                dialog.getFileTag()));
                        addItem(dataFile, 0);
                    }
                }
                scrollToTop();
                if (!toImport.isEmpty()) {
                    for (Integer index : toImport) {
                        CacheUtil.cachingDataFile(dataFile -> refreshItem(dataFile, index), getItem(index));
                    }
                }
                if (!duplicate.isEmpty()) {
                    DialogUtil.confirmImportDuplicateFiles(getContext(), duplicate, materialDialog -> {
                        toImport.clear();
                        runOnUiThread(() -> {
                            int count = 0;
                            for (CharSequence c : duplicate) {
                                String path = c.toString();
                                toImport.add(count++);
                                DataFile dataFile = new DataFile(null, null, InfoUtil.getUserId(), "\n", null, TimeUtil.getCurrentTime(),
                                        dialog.isLocalCaching(), null, null, null, null, dialog.getFileTag(), new FileInfo(path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf(".")),
                                        path,
                                        FileType.getBySuffix(path.substring(path.lastIndexOf(".") + 1)),
                                        null, null,
                                        dialog.getFileTag()));
                                addItem(dataFile, 0);
                            }
                            for (Integer index : toImport) {
                                CacheUtil.cachingDataFile(dataFile -> refreshItem(dataFile, index), getItem(index));
                            }
                        });
                        return null;
                    });
                }
            }

            @Override
            public void onChoose(DataType dataType) {
                FileUtil.selectFiles(getModel().getLauncher(), dataType);
            }
        });
        getModel().getAdapter().setClickListener((itemview, position) -> {
            getModel().setLastClickPosition(position);
            DataFile dataFile = getItem(position);
            if (dataFile.getFileInfo().getType().getDataType() == DataType.TEXT) {
                createIntent(DataFileTextContentActivity.class, false,
                        new IntentData(dataFile.getDataFileId() != null ? "datafile_id" : dataFile.getDataFileIndex() != null ? "datafile_index" : "error", dataFile.getDataFileId() != null ? dataFile.getDataFileId() : dataFile.getDataFileIndex() != null ? dataFile.getDataFileIndex() : 0)
                );

            }
        });
    }

    @Override
    protected void handleRes(int resultCode, Intent data) {
        super.handleRes(resultCode, data);
        if (resultCode == RESULT_OK) {
            if (null != data.getClipData()) {
                List<String> paths = new ArrayList<>();
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    if (uri == null) {
                        ToastUtil.normal("文件获取失败,请重试");
                        return;
                    }
                    String path;
                    try {
                        path = FileUtil.getFileAbsolutePath(this, uri);
                        paths.add(path);
                    } catch (Exception e) {
                        ToastUtil.normal("文件路径解析失败");
                        return;
                    }
                }
                dialog.addPaths(paths);
                paths.clear();
            } else {
                Uri uri = data.getData();
                if (uri == null) {
                    ToastUtil.normal("文件获取失败,请重试");
                    return;
                }
                List<String> paths = new ArrayList<>();
                String path;
                try {
                    path = FileUtil.getFileAbsolutePath(this, uri);
                    paths.add(path);
                } catch (Exception e) {
                    ToastUtil.normal("文件路径解析失败");
                    return;
                }
                dialog.addPaths(paths);
                paths.clear();
            }
        }
    }

}
