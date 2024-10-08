package com.echo.dapc.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.echo.dapc.R;
import com.echo.dapc.base.BaseViewHolder;

public class SystemNoticeViewHolder extends BaseViewHolder {
    public ImageFilterView avatar;
    public TextView title;
    public TextView content;
    public TextView time;

    public SystemNoticeViewHolder(@NonNull View itemView) {
        super(itemView);
        avatar = itemView.findViewById(R.id.avatar);
        title = itemView.findViewById(R.id.title);
        content = itemView.findViewById(R.id.content);
        time = itemView.findViewById(R.id.time);
    }
}
