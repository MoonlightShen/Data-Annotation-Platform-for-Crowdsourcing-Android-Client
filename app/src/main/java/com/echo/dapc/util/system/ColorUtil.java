package com.echo.dapc.util.system;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public final class ColorUtil {

    public static int getColor(@NonNull Context context, @ColorRes int color) {
        return ContextCompat.getColor(context, color);
    }

    public static int parseColor(@NonNull String color){
        return Color.parseColor(color);
    }

}
