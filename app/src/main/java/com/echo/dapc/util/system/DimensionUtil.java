package com.echo.dapc.util.system;

import android.content.Context;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;

public final class DimensionUtil {

    /**
     * 获取dimes值，返回的是【4舍5入取整】的值
     *
     * @param context 上下文
     * @param resId   资源id
     * @return dimes值【4舍5入取整】
     */
    public static int getDimensionPixelSize(@NonNull Context context, @DimenRes int resId) {
        return context.getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取dimes值，返回的是【去余取整】的值
     *
     * @param context 上下文
     * @param resId   资源id
     * @return dimes值【去余取整】
     */
    public static int getDimensionPixelOffset(@NonNull Context context, @DimenRes int resId) {
        return context.getResources().getDimensionPixelOffset(resId);
    }
}
