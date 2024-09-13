package com.echo.dapc.adapter.viewholder.dataset;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.dapc.R;

public class DatasetViewHolder extends RecyclerView.ViewHolder{
    public TextView datasetName;
    public TextView totalData;
    public TextView uploadTime;

    public DatasetViewHolder(@NonNull View itemView) {
        super(itemView);
        datasetName = itemView.findViewById(R.id.dataset_name);
        totalData = itemView.findViewById(R.id.dataset_total_data);
        uploadTime = itemView.findViewById(R.id.dataset_upload_time);
    }

}
