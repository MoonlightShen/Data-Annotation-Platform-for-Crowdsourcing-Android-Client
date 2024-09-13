package com.echo.dapc.bean.ui;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.echo.dapc.bean.enumeration.TagTemplate;


public class Tag {

    private @ColorInt int[] backgroundColor;
    private String content;
    private @ColorInt int[] contentColor;
    private float contentSize;
    private boolean select = false;

    public Tag(int[] backgroundColor, String content, int[] contentColor, float contentSize) {
        this.backgroundColor = backgroundColor;
        this.content = content;
        this.contentColor = contentColor;
        this.contentSize = contentSize;
    }

    public Tag() {
    }

    public static Tag getByTemplate(@NonNull String content, TagTemplate beforeClick, TagTemplate afterClick) {
        Tag tag = new Tag();
        tag.setBackgroundColor(new int[]{beforeClick.getBackgroundColor(), afterClick.getBackgroundColor()});
        tag.setContentColor(new int[]{beforeClick.getContentColor(), afterClick.getContentColor()});
        tag.setContentSize(beforeClick.getContentSize());
        tag.setContent(content);
        return tag;
    }

    public int[] getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int[] backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int[] getContentColor() {
        return contentColor;
    }

    public void setContentColor(int[] contentColor) {
        this.contentColor = contentColor;
    }

    public float getContentSize() {
        return contentSize;
    }

    public void setContentSize(float contentSize) {
        this.contentSize = contentSize;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void reverseSelect(){
        this.select = !this.select;
    }

}
