package com.echo.dapc.bean.enumeration;

import androidx.annotation.ColorInt;

import com.echo.dapc.util.system.ColorUtil;

public enum TagTemplate {
    WHITE_BG_BLACK_TEXT(1, ColorUtil.parseColor("#FFFFFF"), ColorUtil.parseColor("#000000"), 14),
    GRAY_BG_BLACK_TEXT(2, ColorUtil.parseColor("#3FAFBCC3"), ColorUtil.parseColor("#000000"), 14);

    private final int type;
    private final @ColorInt int backgroundColor;
    private final @ColorInt int contentColor;
    private final float contentSize;

    TagTemplate(int type, int backgroundColor, int contentColor, int contentSize) {
        this.type = type;
        this.backgroundColor = backgroundColor;
        this.contentColor = contentColor;
        this.contentSize = contentSize;
    }

    public int getType() {
        return type;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getContentColor() {
        return contentColor;
    }

    public float getContentSize() {
        return contentSize;
    }
}
