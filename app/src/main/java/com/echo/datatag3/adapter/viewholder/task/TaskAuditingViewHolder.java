package com.echo.datatag3.adapter.viewholder.task;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.echo.datatag3.R;
import com.fphoenixcorneae.progressbar.SmartProgressBar;

public class TaskAuditingViewHolder extends TaskViewHolder{
    public TextView uploadedTime;

    public TaskAuditingViewHolder(@NonNull View itemView) {
        super(itemView);
        uploadedTime = itemView.findViewById(R.id.task_upload_time);
    }
}
