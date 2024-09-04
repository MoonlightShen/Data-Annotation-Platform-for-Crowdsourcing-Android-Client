package com.echo.datatag3.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.echo.datatag3.R;
import com.echo.datatag3.adapter.viewholder.TeamNoticeViewHolder;
import com.echo.datatag3.base.BaseItemListAdapter;
import com.echo.datatag3.bean.entity.TeamNotice;

import java.util.ArrayList;
import java.util.List;

public class TeamNoticeListAdapter extends BaseItemListAdapter<TeamNotice, TeamNoticeViewHolder> {
    public TeamNoticeListAdapter() {
        this(new ArrayList<>());
    }

    public TeamNoticeListAdapter(List<TeamNotice> data) {
        super(data);
    }

    @Override
    protected int getViewLayoutId(int viewType) {
        return R.layout.team_notice;
    }

    @NonNull
    @Override
    protected TeamNoticeViewHolder createViewHolder(@NonNull View view, int viewType) {
        return new TeamNoticeViewHolder(view);
    }

    @Override
    protected void bindViewHolder(@NonNull TeamNotice teamNotice, @NonNull TeamNoticeViewHolder viewHolder) {
        //TODO 头像加载
        viewHolder.title.setText("");
        viewHolder.content.setText(teamNotice.getTeamNoticeType().getTag());
    }
}
