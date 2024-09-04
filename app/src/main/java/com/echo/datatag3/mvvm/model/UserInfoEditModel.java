package com.echo.datatag3.mvvm.model;

import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.databinding.Bindable;

import com.echo.datatag3.BR;
import com.echo.datatag3.base.BaseModel;
import com.echo.datatag3.bean.enumeration.Gender;

public class UserInfoEditModel extends BaseModel {
    private String nickname;
    private Gender gender;
    private Integer age;
    private String phone;
    private String email;
    private String introduction;

    private ImageFilterView avatar;

    @Bindable
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        notifyPropertyChanged(BR.nickname);
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Bindable
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
        notifyPropertyChanged(BR.introduction);
    }

    public ImageFilterView getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageFilterView avatar) {
        this.avatar = avatar;
    }
}
