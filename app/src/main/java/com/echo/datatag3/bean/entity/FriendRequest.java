package com.echo.datatag3.bean.entity;

import com.echo.datatag3.bean.converter.ValidationStatusConverter;
import com.echo.datatag3.bean.enumeration.ValidationStatus;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "dt_friend_application")
public class FriendRequest {
    private Long requestId;
    private Long requesterId;
    private Long userId;
    private String validationContent;
    @Convert(converter = ValidationStatusConverter.class, columnType = Integer.class)
    private ValidationStatus validationStatus;
    private String reply;
    private Long requestTime;



    @Generated(hash = 1457249922)
    public FriendRequest(Long requestId, Long requesterId, Long userId,
            String validationContent, ValidationStatus validationStatus, String reply,
            Long requestTime) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.userId = userId;
        this.validationContent = validationContent;
        this.validationStatus = validationStatus;
        this.reply = reply;
        this.requestTime = requestTime;
    }

    @Generated(hash = 1677678717)
    public FriendRequest() {
    }



    public Long getRequesterId() {
        return this.requesterId;
    }

    public void setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ValidationStatus getValidationStatus() {
        return this.validationStatus;
    }

    public void setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }

    public String getReply() {
        return this.reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Long getRequestTime() {
        return this.requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public Long getRequestId() {
        return this.requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getValidationContent() {
        return this.validationContent;
    }

    public void setValidationContent(String validationContent) {
        this.validationContent = validationContent;
    }


}
