package com.echo.datatag3.bean.enumeration;

public enum StatusCode {
    UNKNOWN(0, "未知", ""),
    TOKEN_ERROR(1, "token参数异常", "0002"),
    SUCCESS(2, "成功", "1000"),

    QUERY_USER_NO_USER(2002, "用户不存在", "2002"),
    LOGIN_PASSWORD_FAULT(2003, "密码参数异常", "2003"),
    LOGIN_PASSWORD_ERROR(2005, "密码错误", "2005"),
    LOGIN_ACCOUNT_NOT_EXIST(2008, "账号不存在", "2008"),

    IMAGE_TRANSMISSION_ERROR(3001, "头像文件传输错误", "3001"),

    UPLOAD_DATAFILE_ERROR(4000, "数据集文件上传错误", "4000"),
    UPLOAD_DATAFILE_OVERTIME(4001, "数据集文件缓存超时", "4001"),
    UPLOAD_DATAFILE_VERIFICATION_FAILED(4003, "文件MD5效验失败", "4003");
//    ERROR2007(2007, "账号格式错误", "2007"),
//    ERROR2009(2009, "账号已存在", "2009"),
//    ERROR2011(2011, "手机号格式错误", "2011"),
//    ERROR2013(2013, "手机号已绑定账号", "2013"),
//    ERROR2015(2015, "邮箱格式错误", "2015"),
//    ERROR2017(2015, "邮箱格式错误", "2015"),
//    ERROR2018(2015, "邮箱格式错误", "2015"),
//    ERROR2019(2015, "邮箱格式错误", "2015"),


    private final int index;
    private final String tag;
    private final String code;

    StatusCode(int index, String tag, String code) {
        this.index = index;
        this.tag = tag;
        this.code = code;
    }

    public static StatusCode getByCode(final String code) {
        for (StatusCode value : values()) {
            if (code.equals(value.code)) {
                return value;
            }
        }
        return null;
    }

    public static boolean success(final String statusCode) {
        return SUCCESS.code.equals(statusCode);
    }

    public int getIndex() {
        return index;
    }

    public String getTag() {
        return tag;
    }

    public String getCode() {
        return code;
    }
}

