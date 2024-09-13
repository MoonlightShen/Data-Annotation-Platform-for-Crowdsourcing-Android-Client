package com.echo.dapc.bean.entity;


import androidx.databinding.BaseObservable;

import com.echo.dapc.bean.converter.GenderConverter;
import com.echo.dapc.bean.enumeration.Gender;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "dt_friends")
public class Friend extends BaseObservable {
    private Long userId;
    private String nickname;
    @Convert(converter = GenderConverter.class, columnType = Integer.class)
    private Gender gender;
    private String remark;

    public Friend(Long userId) {
        this.userId = userId;
    }

    @Generated(hash = 1292228821)
    public Friend(Long userId, String nickname, Gender gender, String remark) {
        this.userId = userId;
        this.nickname = nickname;
        this.gender = gender;
        this.remark = remark;
    }

    @Generated(hash = 287143722)
    public Friend() {
    }

    public Friend(Long userId, String nickname, String remark) {
        this.userId = userId;
        this.nickname = nickname;
        this.remark = remark;
    }

    public String getRemark() {
        if (remark == null || remark.isEmpty()) remark = getNickname();
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
