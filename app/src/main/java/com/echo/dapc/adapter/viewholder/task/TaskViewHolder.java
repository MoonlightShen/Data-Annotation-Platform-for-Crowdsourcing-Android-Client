package com.echo.dapc.adapter.viewholder.task;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.dapc.R;

public class TaskViewHolder extends RecyclerView.ViewHolder{
    public TextView taskTitle;
    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        taskTitle = itemView.findViewById(R.id.task_title);
    }

}
