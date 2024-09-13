package com.echo.dapc.bean.enumeration;

public enum DataType {
    UNKNOWN(0, "未知"),
    TEXT(1, "文本"),
    IMAGE(2, "图片"),
    VIDEO(3, "视频"),
    VOICE(4, "语音");

    private final int index;
    private final String tag;

    DataType(int index, String tag){
        this.index = index;
        this.tag = tag;
    }

    public int getIndex(){
        return index;
    }

    public String getTag() {
        return tag;
    }

    public static DataType getByIndex(final int index){
        for (DataType value: values()){
            if (index == value.getIndex()){
                return value;
            }
        }
        return UNKNOWN;
    }

    public static DataType getByTag(final String tag){
        for (DataType value: values()){
            if (tag.equals(value.getTag())){
                return value;
            }
        }
        return UNKNOWN;
    }
}
