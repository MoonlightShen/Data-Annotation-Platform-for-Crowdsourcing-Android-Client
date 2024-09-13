package com.echo.dapc.interfaces.callback.team;

import com.echo.dapc.bean.entity.Team;

import java.util.List;

public interface QueryTeamsCallback {
    void onSuccess(List<Team> teams);
    void onError();
}
