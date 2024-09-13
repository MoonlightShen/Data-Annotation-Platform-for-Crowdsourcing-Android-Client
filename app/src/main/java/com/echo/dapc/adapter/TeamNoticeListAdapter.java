package com.echo.dapc.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.echo.dapc.R;
import com.echo.dapc.adapter.viewholder.TeamNoticeViewHolder;
import com.echo.dapc.base.adapter.BaseRecycleListAdapter;
import com.echo.dapc.bean.entity.TeamNotice;

import java.util.ArrayList;
import java.util.List;

public class TeamNoticeListAdapter extends BaseRecycleListAdapter<TeamNotice, TeamNoticeViewHolder> {
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
