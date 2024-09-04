package com.echo.datatag3.bean.enumeration;

public enum TaskVisibleLevel {
    UNKNOWN(0, "未知"),
    OPEN(1, "公开可见"),
    PARTIALLY(2, "部分可见"),
    INVISIBLE(3, "仅自己可见");

    private final int index;
    private final String tag;

    TaskVisibleLevel(int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    public int getIndex() {
        return index;
    }

    public String getTag() {
        return tag;
    }

    public static TaskVisibleLevel getByIndex(final int index){
        for (TaskVisibleLevel value: values()){
            if (index == value.getIndex()){
                return value;
            }
        }
        return UNKNOWN;
    }
}
