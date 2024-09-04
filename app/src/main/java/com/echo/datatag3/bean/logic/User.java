package com.echo.datatag3.bean.logic;

import androidx.databinding.BaseObservable;

import com.echo.datatag3.bean.enumeration.Gender;

public class User extends BaseObservable {
    private Long userId;
    private String nickname;
    private Gender gender;
    private Integer age;
    private String phone;
    private Integer point;
    private String introduction;
    private String email;

    public User() {
    }

    public User(Long userId) {
        this.userId = userId;
    }

    public User(Long userId, String nickname, Gender gender, Integer age, String phone, Integer point, String introduction, String email) {
        this.userId = userId;
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.point = point;
        this.introduction = introduction;
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
