package com.echo.datatag3.bean.enumeration;

public enum TeamNoticeType {
    INVITED(1,"您被邀请进入团队"),
    KICK_OUT(2, "您已经被移出团队"),
    BECOME_ADMINISTRATOR(3, "您被设置为团队管理员"),
    DISSOLUTION(4, "团队已解散");

    private final int index;
    private final String tag;

    TeamNoticeType(int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    public static TeamNoticeType getByIndex(int index) {
        for (TeamNoticeType value : values()) {
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
