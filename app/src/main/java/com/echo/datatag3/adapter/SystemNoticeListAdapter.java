package com.echo.datatag3.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.echo.datatag3.R;
import com.echo.datatag3.adapter.viewholder.SystemNoticeViewHolder;
import com.echo.datatag3.base.BaseItemListAdapter;
import com.echo.datatag3.bean.enumeration.NoticeType;
import com.echo.datatag3.bean.logic.SystemNotice;
import com.echo.datatag3.util.business.AvatarUtil;
import com.echo.datatag3.util.system.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class SystemNoticeListAdapter extends BaseItemListAdapter<SystemNotice, SystemNoticeViewHolder> {
    public SystemNoticeListAdapter() {
        this(new ArrayList<>());
    }

    public SystemNoticeListAdapter(List<SystemNotice> data) {
        super(data);
    }

    @Override
    protected int getViewLayoutId(int viewType) {
        return R.layout.system_notice;
    }

    @NonNull
    @Override
    protected SystemNoticeViewHolder createViewHolder(@NonNull View view, int viewType) {
        return new SystemNoticeViewHolder(view);
    }

    @Override
    protected void bindViewHolder(@NonNull SystemNotice systemNotice, @NonNull SystemNoticeViewHolder viewHolder) {
        if (systemNotice.getNoticeSourceId() != null) {
            AvatarUtil.loadUserAvatar(systemNotice.getNoticeSourceId(), viewHolder.avatar, false);
        }
        if (systemNotice.getNoticeType() != null) {
            switch (systemNotice.getNoticeType()) {
                case CUSTOM -> {
                    if (systemNotice.getNoticeTitle() != null) {
                        viewHolder.title.setText(systemNotice.getNoticeTitle());
                    }
                    if (systemNotice.getNoticeContent() != null) {
                        viewHolder.content.setText(systemNotice.getNoticeContent());
                    }
                }
                case TASK_REQUEST_APPLY -> {
                    viewHolder.title.setText("您的任务申请已被处理");
                    viewHolder.content.setText("点击查看详细内容");
                }
                case TASK_REQUEST_PENDING_APPROVAL -> {
                    viewHolder.title.setText("您创建的任务有新的申请人");
                    viewHolder.content.setText("点击查看详细内容");
                }
            }
        }
        if (systemNotice.getNoticeTime() != null) {
            viewHolder.time.setText(TimeUtil.format(systemNotice.getNoticeTime()));
        }
    }

}
