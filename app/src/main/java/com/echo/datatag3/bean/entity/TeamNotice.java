package com.echo.datatag3.bean.entity;

import com.echo.datatag3.bean.converter.TeamNoticeTypeConverter;
import com.echo.datatag3.bean.converter.ValidationStatusConverter;
import com.echo.datatag3.bean.enumeration.TeamNoticeType;
import com.echo.datatag3.bean.enumeration.ValidationStatus;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "dt_team_notice")
public class TeamNotice {
    private Long noticeId;
    private Long teamId;
    private Long userId;
    private String avatarUrl;
    @Convert(converter = TeamNoticeTypeConverter.class, columnType = Integer.class)
    private TeamNoticeType teamNoticeType;
    @Convert(converter = ValidationStatusConverter.class, columnType = Integer.class)
    private ValidationStatus validationStatus;
    private Long noticeTime;



    @Generated(hash = 566000720)
    public TeamNotice(Long noticeId, Long teamId, Long userId, String avatarUrl,
            TeamNoticeType teamNoticeType, ValidationStatus validationStatus,
            Long noticeTime) {
        this.noticeId = noticeId;
        this.teamId = teamId;
        this.userId = userId;
        this.avatarUrl = avatarUrl;
        this.teamNoticeType = teamNoticeType;
        this.validationStatus = validationStatus;
        this.noticeTime = noticeTime;
    }

    @Generated(hash = 102470202)
    public TeamNotice() {
    }



    public Long getNoticeId() {
        return this.noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getTeamId() {
        return this.teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public ValidationStatus getValidationStatus() {
        return this.validationStatus;
    }

    public void setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }

    public Long getNoticeTime() {
        return this.noticeTime;
    }

    public void setNoticeTime(Long noticeTime) {
        this.noticeTime = noticeTime;
    }

    public TeamNoticeType getTeamNoticeType() {
        return this.teamNoticeType;
    }

    public void setTeamNoticeType(TeamNoticeType teamNoticeType) {
        this.teamNoticeType = teamNoticeType;
    }


}
