package com.echo.dapc.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.echo.dapc.R;
import com.echo.dapc.adapter.viewholder.UserViewHolder;
import com.echo.dapc.base.adapter.BaseRecycleListAdapter;
import com.echo.dapc.bean.enumeration.Gender;
import com.echo.dapc.bean.logic.User;
import com.echo.dapc.util.business.AvatarUtil;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends BaseRecycleListAdapter<User, UserViewHolder> {
    public UserListAdapter() {
        this(new ArrayList<>());
    }

    public UserListAdapter(List<User> data) {
        super(data);
    }

    @Override
    protected int getViewLayoutId(int viewType) {
        return R.layout.user;
    }

    @NonNull
    @Override
    protected UserViewHolder createViewHolder(@NonNull View view, int viewType) {
        return new UserViewHolder(view);
    }

    @Override
    protected void bindViewHolder(@NonNull User user, @NonNull UserViewHolder viewHolder) {
        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, user.getUserId(), viewHolder.avatar, true);
        viewHolder.nickname.setText(user.getNickname());
    }
}
