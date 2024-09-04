package com.echo.datatag3.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseViewHolder;

public class DataFileTextContentViewHolder extends BaseViewHolder {
    public final TextView content;

    public DataFileTextContentViewHolder(@NonNull View itemView) {
        super(itemView);
        content = itemView.findViewById(R.id.datafile_text_content);
    }
}
