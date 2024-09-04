package com.echo.datatag3.adapter;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;

import com.echo.datatag3.R;
import com.echo.datatag3.adapter.viewholder.DataFileViewHolder;
import com.echo.datatag3.base.BaseItemListAdapter;
import com.echo.datatag3.base.BaseRecycleViewAdapter;
import com.echo.datatag3.bean.logic.DataFile;
import com.echo.datatag3.util.system.TimeUtil;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class DataFileListAdapter extends BaseItemListAdapter<DataFile, DataFileViewHolder> {
    public DataFileListAdapter() {
        this(new ArrayList<>());
    }

    public DataFileListAdapter(List<DataFile> data) {
        super(data);
    }

    @Override
    protected int getViewLayoutId(int viewType) {
        return R.layout.datafile;
    }

    @NonNull
    @Override
    protected DataFileViewHolder createViewHolder(@NonNull View view, int viewType) {
        return new DataFileViewHolder(view);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void bindViewHolder(@NonNull DataFile dataFile, @NonNull DataFileViewHolder viewHolder) {
        if (dataFile.getFileInfo()!=null&&dataFile.getFileInfo().getName()!=null){
            viewHolder.fileName.setText(dataFile.getFileInfo().getName());
        }
        if (dataFile.getImportTime()!=null){
            viewHolder.importTime.setText(TimeUtil.format(dataFile.getImportTime(), TimeUtil.zh_yyyyMMddHHmm));
        }
        if (dataFile.getLocalCaching()!=null&&dataFile.getLocalCaching()&&dataFile.getCacheFilePath()!=null&&!dataFile.getCacheFilePath().isEmpty()){
            viewHolder.localCachingStatus.setText("已缓存");
        }
        viewHolder.uploadTime.setText(dataFile.getUploadTime()!=null&&dataFile.getUploadTime()!=0?TimeUtil.format(dataFile.getUploadTime(), TimeUtil.zh_yyyyMMddHHmm):"未上传");
        if (dataFile.getFileInfo()!=null&&dataFile.getFileInfo().getSize()!=null){
            double size = dataFile.getFileInfo().getSize();
            String unit = "B";
            if (size>1024){
                size/=1024;
                unit = "KB";
            }
            if (size>1024){
                size/=1024;
                unit = "MB";
            }
            if (size>1024){
                size/=1024;
                unit = "GB";
            }
            viewHolder.fileSize.setText(String.format("%.2f %s", size, unit));
        }
        if (dataFile.getDataNum()!=null){
            viewHolder.dataNum.setText(String.valueOf(dataFile.getDataNum()));
        }
        if (dataFile.getFileInfo()!=null&&dataFile.getFileInfo().getType()!=null){
            viewHolder.dataType.setText(dataFile.getFileInfo().getType().getDataType().getTag());
        }
    }

}
