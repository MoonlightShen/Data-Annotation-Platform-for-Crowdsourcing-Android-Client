package com.echo.dapc.interfaces.callback.teamnotice;

import com.echo.dapc.bean.entity.TeamNotice;

import java.util.List;

public interface LoadAllTeamNoticesCallback {
    void onSuccess(List<TeamNotice> notices);
    void onError(String errorMessage);
}
