package com.echo.datatag3.bean.logic;

import com.echo.datatag3.bean.entity.LocalSystemNotice;
import com.echo.datatag3.bean.enumeration.NoticeType;
import com.echo.datatag3.interfaces.bean.Local2Logic;
import com.echo.datatag3.interfaces.bean.Logic2Local;

public final class SystemNotice implements Local2Logic<LocalSystemNotice, SystemNotice>, Logic2Local<SystemNotice, LocalSystemNotice> {
    private Long noticeIndex;  //本地数据库主键
    private Long noticeId;  //逻辑主键
    private Long noticeSourceId; //通知来源者ID
    private NoticeType noticeType;  //通知类型
    private String noticeTitle;  //通知标题
    private String noticeContent;  //通知内容
    private Boolean read;  //已读状态
    private Long noticeTime;  //通知产生的时间

    public SystemNotice() {
    }

    public SystemNotice(Long noticeIndex, Long noticeId, Long noticeSourceId, NoticeType noticeType, String noticeTitle, String noticeContent, Boolean read, Long noticeTime) {
        this.noticeIndex = noticeIndex;
        this.noticeId = noticeId;
        this.noticeSourceId = noticeSourceId;
        this.noticeType = noticeType;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.read = read;
        this.noticeTime = noticeTime;
    }

    public Long getNoticeIndex() {
        return noticeIndex;
    }

    public void setNoticeIndex(Long noticeIndex) {
        this.noticeIndex = noticeIndex;
    }

    public Long getNoticeId() {
        return this.noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return this.noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return this.noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Long getNoticeTime() {
        return this.noticeTime;
    }

    public void setNoticeTime(Long noticeTime) {
        this.noticeTime = noticeTime;
    }

    public Boolean getRead() {
        return this.read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Long getNoticeSourceId() {
        return noticeSourceId;
    }

    public void setNoticeSourceId(Long noticeSourceId) {
        this.noticeSourceId = noticeSourceId;
    }

    public NoticeType getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(NoticeType noticeType) {
        this.noticeType = noticeType;
    }

    @Override
    public SystemNotice fromLocal(LocalSystemNotice localSystemNotice) {
        return new SystemNotice(localSystemNotice.getNoticeIndex(), localSystemNotice.getNoticeId(),
                localSystemNotice.getNoticeSourceId(), NoticeType.getByIndex(localSystemNotice.getNoticeType()), localSystemNotice.getNoticeTitle(),
                localSystemNotice.getNoticeContent(), localSystemNotice.getRead(), localSystemNotice.getNoticeTime());
    }

    @Override
    public LocalSystemNotice fromLogic(SystemNotice systemNotice) {
        return new LocalSystemNotice(systemNotice.noticeIndex, systemNotice.noticeId, systemNotice.noticeSourceId,
                systemNotice.noticeType.getIndex(), systemNotice.noticeTitle,
                systemNotice.noticeContent, systemNotice.read, systemNotice.noticeTime);
    }
}
