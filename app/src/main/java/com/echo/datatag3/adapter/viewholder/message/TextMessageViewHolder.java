package com.echo.datatag3.adapter.viewholder.message;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.echo.datatag3.R;

public class TextMessageViewHolder extends ChatMessageViewHolder {
    public ImageView userAvatar;
    public TextView messageText;

    public TextMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        userAvatar = itemView.findViewById(R.id.text_message_user_avatar);
        messageText = itemView.findViewById(R.id.text_message_content);
    }
}
