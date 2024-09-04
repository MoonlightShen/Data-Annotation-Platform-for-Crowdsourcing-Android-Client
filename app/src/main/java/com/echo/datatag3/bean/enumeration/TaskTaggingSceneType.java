package com.echo.datatag3.bean.enumeration;

public enum TaskTaggingSceneType {
    UNKNOWN(0, "未知"),
    LABEL_CLASSIFICATION(1, "标签分类");

    private final int index;
    private final String tag;

    TaskTaggingSceneType(int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    public int getIndex() {
        return index;
    }

    public String getTag() {
        return tag;
    }

    public static TaskTaggingSceneType getByIndex(final int index){
        for (TaskTaggingSceneType value: values()){
            if (index == value.getIndex()){
                return value;
            }
        }
        return UNKNOWN;
    }

}
