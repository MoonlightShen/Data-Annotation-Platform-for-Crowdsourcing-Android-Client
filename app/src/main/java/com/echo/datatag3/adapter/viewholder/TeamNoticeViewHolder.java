package com.echo.datatag3.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseViewHolder;

public class TeamNoticeViewHolder extends BaseViewHolder {
    public final ImageFilterView avatar;
    public final TextView title;
    public final TextView content;

    public TeamNoticeViewHolder(@NonNull View itemView) {
        super(itemView);
        avatar = itemView.findViewById(R.id.avatar);
        title = itemView.findViewById(R.id.title);
        content = itemView.findViewById(R.id.content);
    }

}
