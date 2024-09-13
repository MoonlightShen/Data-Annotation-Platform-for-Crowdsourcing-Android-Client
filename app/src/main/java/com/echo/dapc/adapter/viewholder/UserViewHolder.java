package com.echo.dapc.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.echo.dapc.R;
import com.echo.dapc.base.BaseViewHolder;

public class UserViewHolder extends BaseViewHolder {
    public ImageFilterView avatar;
    public TextView nickname;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        avatar = itemView.findViewById(R.id.avatar);
        nickname = itemView.findViewById(R.id.nickname);
    }
}
