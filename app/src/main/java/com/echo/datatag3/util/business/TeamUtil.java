package com.echo.datatag3.util.business;

import androidx.annotation.NonNull;

import com.echo.datatag3.bean.entity.Team;
import com.echo.datatag3.interfaces.callback.team.QueryTeamsCallback;

import java.util.ArrayList;
import java.util.List;

public final class TeamUtil {

    public static void queryTeams(@NonNull QueryTeamsCallback callback){
        new Thread(() -> {
            List<Team> teams = new ArrayList<>();
            //TODO 网络请求查询团队
            callback.onSuccess(teams);
        }).start();
    }

}
