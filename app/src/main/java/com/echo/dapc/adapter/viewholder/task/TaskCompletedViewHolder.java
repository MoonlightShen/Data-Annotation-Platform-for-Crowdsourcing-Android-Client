package com.echo.dapc.adapter.viewholder.task;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.echo.dapc.R;

public class TaskCompletedViewHolder extends TaskViewHolder{
    public TextView answerNum;
    public TextView likeNum;
    public TextView starNum;
    public TextView readNum;
    public TextView applyUsersNum;
    public ImageFilterView applyUser1;
    public ImageFilterView applyUser2;
    public ImageFilterView applyUser3;
    public ImageFilterView applyUser4;
    public ImageFilterView applyUser5;

    public TaskCompletedViewHolder(@NonNull View itemView) {
        super(itemView);
        answerNum = itemView.findViewById(R.id.task_answer_number);
        likeNum = itemView.findViewById(R.id.like_num);
        starNum = itemView.findViewById(R.id.star_num);
        readNum = itemView.findViewById(R.id.read_num);
        applyUsersNum = itemView.findViewById(R.id.task_apply_users_num);
        applyUser1 = itemView.findViewById(R.id.apply_user_avatar_1);
        applyUser2 = itemView.findViewById(R.id.apply_user_avatar_2);
        applyUser3 = itemView.findViewById(R.id.apply_user_avatar_3);
        applyUser4 = itemView.findViewById(R.id.apply_user_avatar_4);
        applyUser5 = itemView.findViewById(R.id.apply_user_avatar_5);
    }
}
