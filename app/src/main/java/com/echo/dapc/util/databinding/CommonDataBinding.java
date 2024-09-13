package com.echo.dapc.util.databinding;

import android.view.View;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.echo.dapc.bean.enumeration.Gender;
import com.echo.dapc.util.system.TimeUtil;

public final class CommonDataBinding {

    @BindingAdapter("android:text")
    public static void setText(TextView textView, int value){
        textView.setText(String.valueOf(value));
    }

    @BindingAdapter("android:text")
    public static void setText(TextView textView, Integer value) {
        textView.setText(value==null?"":String.valueOf(value.intValue()));
    }

    @BindingAdapter("android:text")
    public static void setText(TextView textView, long value){
        textView.setText(String.valueOf(value));
    }

    @BindingAdapter("android:text")
    public static void setText(TextView textView, Long value) {
        textView.setText(value == null ? "" : String.valueOf(value.longValue()));
    }

    @BindingAdapter("visibility")
    public static void setVisibility(View view, boolean flag) {
        view.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("zh_yyyyMMddHHmm")
    public static void setTime1(TextView textView, Long time) {
        textView.setText(time == null || time == 0 ? "" : TimeUtil.format(time, TimeUtil.zh_yyyyMMddHHmm));
    }

    @BindingAdapter("zh_ddHHmmSS")
    public static void setTime2(TextView textView, Long time) {
        textView.setText(time == null || time == 0 ? "" : TimeUtil.format(time, TimeUtil.zh_ddHHmmSS));
    }

    @BindingAdapter("gender")
    public static void setGender(TextView textView, Gender gender) {
        if (gender == null) textView.setText("未知");
        else textView.setText(gender.getTag());
    }

}
