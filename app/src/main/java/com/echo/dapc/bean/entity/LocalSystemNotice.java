package com.echo.dapc.bean.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

@Entity(nameInDb = "dt_system_notice")
public class LocalSystemNotice {
    @Id(autoincrement = true)
    @Unique
    private Long noticeIndex;  //本地数据库主键
    private Long noticeId;  //逻辑主键
    private Long noticeSourceId; //通知来源者ID
    private Integer noticeType;  //通知类型
    private String noticeTitle;  //通知标题
    private String noticeContent;  //通知内容
    private Boolean read;  //已读状态
    private Long noticeTime;  //通知产生的时间

    @Generated(hash = 18187489)
    public LocalSystemNotice(Long noticeIndex, Long noticeId, Long noticeSourceId,
                             Integer noticeType, String noticeTitle, String noticeContent,
                             Boolean read, Long noticeTime) {
        this.noticeIndex = noticeIndex;
        this.noticeId = noticeId;
        this.noticeSourceId = noticeSourceId;
        this.noticeType = noticeType;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.read = read;
        this.noticeTime = noticeTime;
    }

    @Generated(hash = 291977211)
    public LocalSystemNotice() {
    }

    public Long getNoticeIndex() {
        return this.noticeIndex;
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

    public Long getNoticeSourceId() {
        return this.noticeSourceId;
    }

    public void setNoticeSourceId(Long noticeSourceId) {
        this.noticeSourceId = noticeSourceId;
    }

    public Integer getNoticeType() {
        return this.noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
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

    public Boolean getRead() {
        return this.read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Long getNoticeTime() {
        return this.noticeTime;
    }

    public void setNoticeTime(Long noticeTime) {
        this.noticeTime = noticeTime;
    }


}
