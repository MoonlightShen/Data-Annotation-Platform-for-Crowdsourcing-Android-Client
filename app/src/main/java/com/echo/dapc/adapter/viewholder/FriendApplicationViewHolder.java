package com.echo.dapc.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.echo.dapc.R;
import com.echo.dapc.base.BaseViewHolder;

public class FriendApplicationViewHolder extends BaseViewHolder {
    public final ImageFilterView avatar;
    public final TextView title;
    public final TextView content;
    public final TextView validationStatus;

    public FriendApplicationViewHolder(@NonNull View itemView) {
        super(itemView);
        avatar = itemView.findViewById(R.id.avatar);
        title = itemView.findViewById(R.id.title);
        content = itemView.findViewById(R.id.content);
        validationStatus = itemView.findViewById(R.id.validate_status);
    }

}
