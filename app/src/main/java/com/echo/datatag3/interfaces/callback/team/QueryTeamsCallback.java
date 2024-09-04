package com.echo.datatag3.interfaces.callback.team;

import com.echo.datatag3.bean.entity.Team;

import java.util.List;

public interface QueryTeamsCallback {
    void onSuccess(List<Team> teams);
    void onError();
}
