package com.echo.datatag3.bean.enumeration;

public enum Gender {
    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");

    private final int index;
    private final String tag;

    Gender(int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    public static Gender getByIndex(final int index){
        for (Gender value: values()){
            if (index == value.getIndex()){
                return value;
            }
        }
        return UNKNOWN;
    }

    public int getIndex() {
        return index;
    }

    public String getTag() {
        return tag;
    }

    public static boolean isFemale(Gender gender){
        return gender.getIndex() == FEMALE.getIndex();
    }

    public static boolean isMale(Gender gender){
        return gender.getIndex() != FEMALE.getIndex();
    }

}
