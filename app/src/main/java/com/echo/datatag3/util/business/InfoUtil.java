package com.echo.datatag3.util.business;

import com.echo.datatag3.bean.enumeration.Gender;
import com.echo.datatag3.util.system.SharedSharedPreferencesUtil;

public class InfoUtil {
    private static final String ACCOUNT_KEY = "current_account";
    private static final String PASSWORD_KEY = "current_password";
    private static final String TOKEN_KEY = "current_token";
    private static final String USER_NAME = "current_name";
    private static final String USER_ID = "current_user_id";
    private static final String POINT = "current_point";
    private static final String USER_GENDER = "current_gender";

    public static String getAccount() {
        return SharedSharedPreferencesUtil.getString(ACCOUNT_KEY, null);
    }

    public static void refreshAccount(String value) {
        SharedSharedPreferencesUtil.addValue(ACCOUNT_KEY, value);
    }

    public static String getPassword() {
        return SharedSharedPreferencesUtil.getString(PASSWORD_KEY, null);
    }

    public static void refreshPassword(String value) {
        SharedSharedPreferencesUtil.addValue(PASSWORD_KEY, value);
    }

    public static String getToken() {
        return SharedSharedPreferencesUtil.getString(TOKEN_KEY, null);
    }

    public static void refreshToken(String value) {
        SharedSharedPreferencesUtil.addValue(TOKEN_KEY, value);
    }

    public static String getUserName() {
        return SharedSharedPreferencesUtil.getString(USER_NAME, null);
    }

    public static void refreshUserName(String value) {
        SharedSharedPreferencesUtil.addValue(USER_NAME, value);
    }

    public static Long getUserId() {
        return SharedSharedPreferencesUtil.getLong(USER_ID, 0);
    }

    public static void refreshUserId(Long value) {
        SharedSharedPreferencesUtil.addValue(USER_ID, value);
    }

    public static Integer getPoint() {
        return SharedSharedPreferencesUtil.getInteger(POINT, 0);
    }

    public static void refreshPoint(Integer value) {
        SharedSharedPreferencesUtil.addValue(POINT, value);
    }

    public static Gender getGender() {
        return Gender.getByIndex(SharedSharedPreferencesUtil.getInteger(USER_GENDER, 0));
    }

    public static void refreshGender(Gender gender) {
        SharedSharedPreferencesUtil.addValue(USER_GENDER, gender.getIndex());
    }

}
