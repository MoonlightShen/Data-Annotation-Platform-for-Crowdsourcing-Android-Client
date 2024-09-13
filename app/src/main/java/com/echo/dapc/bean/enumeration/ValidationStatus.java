package com.echo.dapc.bean.enumeration;

public enum ValidationStatus {
    PENDING(1,"待处理"),
    AGREE(2, "已同意"),
    IGNORE(3, "已忽略"),
    REFUSE(4, "已拒绝");

    private final int index;
    private final String tag;

    ValidationStatus(int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    public static ValidationStatus getByIndex(int index) {
        for (ValidationStatus value : values()) {
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
