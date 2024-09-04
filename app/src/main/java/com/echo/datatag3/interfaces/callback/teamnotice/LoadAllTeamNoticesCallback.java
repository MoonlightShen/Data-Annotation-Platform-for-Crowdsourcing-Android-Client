package com.echo.datatag3.interfaces.callback.teamnotice;

import com.echo.datatag3.bean.entity.TeamNotice;

import java.util.List;

public interface LoadAllTeamNoticesCallback {
    void onSuccess(List<TeamNotice> notices);
    void onError(String errorMessage);
}
