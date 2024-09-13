package com.echo.dapc.bean.enumeration;

public enum TaskStartStrategy {
    UNKNOWN(0, "未知"),
    EDITING(1, "编辑中"),
    AUDITING(2, "审核中"),
    ABNORMAL(3, "异常"),
    RUNNING(4, "进行中"),
    PAUSE(5, "暂停"),
    COMPLETED(6, "已完成");

    private final int index;
    private final String tag;

    TaskStartStrategy(int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    public int getIndex() {
        return index;
    }

    public String getTag() {
        return tag;
    }
}
