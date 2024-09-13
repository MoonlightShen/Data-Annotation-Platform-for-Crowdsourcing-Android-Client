package com.echo.dapc.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.dapc.R;

public class MessageWindowViewHolder extends RecyclerView.ViewHolder {
    public ImageFilterView avatar;
    public TextView name;
    public TextView lastMessage;
    public TextView lastMessageTime;
    public View unreadNotices;

    public MessageWindowViewHolder(@NonNull View itemView) {
        super(itemView);
        avatar = itemView.findViewById(R.id.message_window_avatar);
        name = itemView.findViewById(R.id.message_window_name);
        lastMessage = itemView.findViewById(R.id.message_window_last_message);
        lastMessageTime = itemView.findViewById(R.id.message_window_last_message_window_time);
        unreadNotices = itemView.findViewById(R.id.unread_notices);
    }

}
