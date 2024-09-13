package com.echo.dapc.bean.enumeration;

public enum MemberType {
    CREATOR(1, "创建者"),
    ADMINISTRATORS(2, "管理员"),
    MEMBER(3, "普通成员");

    private final int index;
    private final String tag;

    MemberType(int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    public int getIndex() {
        return index;
    }

    public String getTag() {
        return tag;
    }

    public static MemberType getByIndex(final int index){
        for (MemberType value: values()){
            if (index == value.getIndex()){
                return value;
            }
        }
        return null;
    }

    public static MemberType getByTag(final String tag){
        for (MemberType value: values()){
            if (tag.equals(value.getTag())){
                return value;
            }
        }
        return null;
    }
}
