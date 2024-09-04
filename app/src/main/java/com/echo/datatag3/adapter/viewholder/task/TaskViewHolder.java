package com.echo.datatag3.adapter.viewholder.task;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.datatag3.R;

public class TaskViewHolder extends RecyclerView.ViewHolder{
    public TextView taskTitle;
    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        taskTitle = itemView.findViewById(R.id.task_title);
    }

}
