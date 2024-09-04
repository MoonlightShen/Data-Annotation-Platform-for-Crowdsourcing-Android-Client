package com.echo.datatag3.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseViewHolder;

public class UserViewHolder extends BaseViewHolder {
    public ImageFilterView avatar;
    public TextView nickname;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        avatar = itemView.findViewById(R.id.avatar);
        nickname = itemView.findViewById(R.id.nickname);
    }
}
