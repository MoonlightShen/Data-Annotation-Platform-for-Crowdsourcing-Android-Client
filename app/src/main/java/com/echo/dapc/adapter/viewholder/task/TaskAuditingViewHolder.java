package com.echo.dapc.adapter.viewholder.task;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.echo.dapc.R;

public class TaskAuditingViewHolder extends TaskViewHolder{
    public TextView uploadedTime;

    public TaskAuditingViewHolder(@NonNull View itemView) {
        super(itemView);
        uploadedTime = itemView.findViewById(R.id.task_upload_time);
    }
}
