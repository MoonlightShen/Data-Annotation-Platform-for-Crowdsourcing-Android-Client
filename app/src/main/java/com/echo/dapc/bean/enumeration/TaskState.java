package com.echo.dapc.bean.enumeration;

public enum TaskState {
    UNKNOWN(0, "未知"),
    EDITING(1, "编辑中"),
    AUDITING(2, "审核中"),
    ABNORMAL(3, "异常"),
    RUNNING(4, "进行中"),
    PAUSE(5, "暂停"),
    COMPLETED(6, "已完成");

    private final int index;
    private final String tag;

    TaskState(int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    public static TaskState getByIndex(int index) {
        for (TaskState value : values()) {
            if (value.getIndex() == index) {
                return value;
            }
        }
        return null;
    }

    public static TaskState getByTag(String tag) {
        for (TaskState value : values()) {
            if (tag.equals(value.getTag())) {
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
