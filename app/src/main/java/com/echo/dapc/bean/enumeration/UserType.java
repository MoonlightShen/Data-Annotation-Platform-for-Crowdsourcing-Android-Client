package com.echo.dapc.bean.enumeration;

public enum UserType {
    REVIEWED(1, "待审核用户"),
    NORMAL(2, "普通用户"),
    ADMINISTRATOR(3, "管理员");

    private final int index;
    private final String tag;

    UserType(int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    public int getIndex() {
        return index;
    }

    public String getTag() {
        return tag;
    }

    public static UserType getByIndex(final int index){
        for (UserType value: values()){
            if (index == value.getIndex()){
                return value;
            }
        }
        return null;
    }

    public static UserType getByTag(final String tag){
        for (UserType value: values()){
            if (tag.equals(value.getTag())){
                return value;
            }
        }
        return null;
    }
}
