package com.echo.dapc.bean.enumeration;

public enum ChatType {
    USER(1, "用户"),
    TEAM(2, "团队");

    private final int index;
    private final String tag;

    ChatType(int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    public static ChatType getByIndex(int index) {
        for (ChatType value : values()) {
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
