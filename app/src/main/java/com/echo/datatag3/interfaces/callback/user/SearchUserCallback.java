package com.echo.datatag3.interfaces.callback.user;

import com.classic.common.MultipleStatusView;
import com.echo.datatag3.adapter.UserListAdapter;

public interface SearchUserCallback {
    void onSearch(String searchText, MultipleStatusView statusView, UserListAdapter adapter);
}

