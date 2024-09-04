package com.echo.datatag3.bean.enumeration;

public enum MessageContentType {
    UNKNOWN(0, "未知"),
    TEXT(1, "文字"),
    IMAGE(2, "图片"),
    VIDEO(3, "视频"),
    VOICE(4, "语音"),
    FILE(5, "文件");

    public static final int UNKNOWN_INDEX = 0;
    public static final int TEXT_INDEX = 1;
    public static final int IMAGE_INDEX = 2;
    public static final int VIDEO_INDEX = 3;
    public static final int VOICE_INDEX = 4;
    public static final int FILE_INDEX = 5;

    private final int index;
    private final String tag;

    MessageContentType(int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    public static MessageContentType getByIndex(int index) {
        for (MessageContentType value : values()) {
            if (value.getIndex() == index) {
                return value;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public String getTag() {
        return tag;
    }
}
