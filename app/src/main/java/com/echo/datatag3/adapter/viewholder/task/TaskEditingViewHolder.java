package com.echo.datatag3.adapter.viewholder.task;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.echo.datatag3.R;

public class TaskEditingViewHolder extends TaskViewHolder{
    public final LinearLayout tags;
    public final TextView unitPoint;
    public final TextView groupSize;
    public final TextView endTime;
    public final TextView taggingScene;
//    public final TextView visibleLevel;

    public TaskEditingViewHolder(@NonNull View itemView) {
        super(itemView);
        tags = itemView.findViewById(R.id.task_linear_tags);
        unitPoint = itemView.findViewById(R.id.task_tv_unit_point);
        groupSize = itemView.findViewById(R.id.task_tv_group_size);
        endTime = itemView.findViewById(R.id.task_tv_end_time);
        taggingScene = itemView.findViewById(R.id.task_tv_tagging_scene_type);
//        visibleLevel = itemView.findViewById(R.id.task_tv_visible_level);
    }
}
