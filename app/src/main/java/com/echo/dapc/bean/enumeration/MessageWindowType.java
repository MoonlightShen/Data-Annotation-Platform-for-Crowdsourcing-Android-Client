package com.echo.dapc.bean.enumeration;

public enum MessageWindowType {
    FRIEND(1, "朋友"),
    TEAM(2, "团队");

    private final int index;
    private final String tag;

    MessageWindowType(int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    public static MessageWindowType getById(int id) {
        for (MessageWindowType value : values()) {
            if (value.getIndex() == id) {
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
