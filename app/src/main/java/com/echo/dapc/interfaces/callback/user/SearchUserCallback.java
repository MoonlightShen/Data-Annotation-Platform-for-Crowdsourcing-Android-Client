package com.echo.dapc.interfaces.callback.user;

import com.classic.common.MultipleStatusView;
import com.echo.dapc.adapter.UserListAdapter;

public interface SearchUserCallback {
    void onSearch(String searchText, MultipleStatusView statusView, UserListAdapter adapter);
}

