package com.echo.datatag3.bean.enumeration;

public enum NoticeType {
    UNKNOWN(0, "未知"),
    CUSTOM(1, "自定义"),
    TASK_REQUEST_PENDING_APPROVAL(2, "待审批的任务申请"),
    TASK_REQUEST_APPLY(3, "任务申请的回复");

    private final int index;
    private final String tag;

    NoticeType(int index, String tag){
        this.index = index;
        this.tag = tag;
    }

    public int getIndex(){
        return index;
    }

    public String getTag() {
        return tag;
    }

    public static NoticeType getByIndex(final Integer index){
        if (index!=null&&index!=0) {
            for (NoticeType value: values()){
                if (index == value.getIndex()){
                    return value;
                }
            }
        }
        return UNKNOWN;
    }

    public static NoticeType getByTag(final String tag){
        if (tag!=null&&!tag.isEmpty()) {
            for (NoticeType value: values()){
                if (tag.equals(value.getTag())){
                    return value;
                }
            }
        }
        return UNKNOWN;
    }
}
