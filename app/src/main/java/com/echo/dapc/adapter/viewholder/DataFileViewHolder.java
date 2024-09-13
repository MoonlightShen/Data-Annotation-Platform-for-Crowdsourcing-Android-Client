package com.echo.dapc.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.dapc.R;

public class DataFileViewHolder extends RecyclerView.ViewHolder {
    public final TextView fileName;
    public final TextView importTime;
    public final TextView localCachingStatus;
    public final TextView uploadTime;
    public final TextView fileSize;
    public final TextView dataNum;
    public final TextView dataType;

    public DataFileViewHolder(@NonNull View itemView) {
        super(itemView);
        fileName = itemView.findViewById(R.id.datafile_file_name);
        importTime = itemView.findViewById(R.id.datafile_import_time);
        localCachingStatus = itemView.findViewById(R.id.datafile_local_caching_status);
        uploadTime = itemView.findViewById(R.id.datafile_upload_time);
        fileSize = itemView.findViewById(R.id.datafile_file_size);
        dataNum = itemView.findViewById(R.id.datafile_data_num);
        dataType = itemView.findViewById(R.id.datafile_data_type);
    }

}
