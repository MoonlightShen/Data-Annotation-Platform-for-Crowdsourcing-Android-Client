package com.echo.datatag3.util.business;

import android.util.Log;

import androidx.annotation.NonNull;

import com.echo.datatag3.bean.entity.Friend;
import com.echo.datatag3.bean.enumeration.Gender;
import com.echo.datatag3.bean.enumeration.StatusCode;
import com.echo.datatag3.bean.logic.User;
import com.echo.datatag3.interfaces.callback.common.CommonCallback;
import com.echo.datatag3.interfaces.callback.common.CommonEntityListCallback;
import com.echo.datatag3.interfaces.callback.register.RegisterCheckCallback;
import com.echo.datatag3.interfaces.callback.user.LoginCallback;
import com.echo.datatag3.interfaces.callback.user.QueryUserCallback;
import com.echo.datatag3.interfaces.callback.user.QueryUserNicknameCallback;
import com.echo.datatag3.interfaces.callback.user.QueryUsersCallback;
import com.echo.datatag3.interfaces.callback.user.UpdateUserAvatarCallback;
import com.echo.datatag3.interfaces.callback.user.UpdateUserInfoCallback;
import com.echo.datatag3.util.network.request.UserRequest;
import com.echo.datatag3.util.network.response.LoginRes;
import com.echo.datatag3.util.network.response.common.StatusCodeRes;
import com.echo.datatag3.util.network.response.user.FriendListRes;
import com.echo.datatag3.util.network.response.user.UserInfoRes;
import com.echo.datatag3.util.network.response.user.UserListRes;
import com.echo.datatag3.util.network.response.user.UserNicknameRes;
import com.echo.datatag3.util.system.EncryptionUtil;
import com.echo.datatag3.util.system.SharedSharedPreferencesUtil;
import com.echo.datatag3.util.system.ThreadUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class UserUtil {

    public static long getId() {
        return SharedSharedPreferencesUtil.getLong("user_id", 0);
    }


    public static void register(@NonNull final RegisterCheckCallback callback, String account, String phone, String email, String password) {
        ThreadUtil.runInBackground(() -> {
            try {
                if (!UserRequest.getAccountCheckRes(account)) {
                    if (phone == null) {
                        if (!UserRequest.getEmailCheckRes(email)) {
                            StatusCodeRes code = UserRequest.getRegisterRes(account, "", email, password);
                            if (StatusCode.SUCCESS == StatusCode.getByCode(code.code)) {
                                callback.onSuccess();
                            } else {
                                callback.onFailure(code.code);
                            }
                        } else {
                            callback.onEmailExist();
                        }
                    } else {
                        if (!UserRequest.getPhoneCheckRes(phone)) {
                            StatusCodeRes code = UserRequest.getRegisterRes(account, phone, "", password);
                            if (StatusCode.SUCCESS == StatusCode.getByCode(code.code)) {
                                callback.onSuccess();
                            } else {
                                callback.onFailure(code.code);
                            }
                        } else {
                            callback.onPhoneExist();
                        }
                    }
                } else {
                    callback.onAccountExist();
                }
            } catch (IOException e) {
                callback.onNetworkError(e.getMessage());
            }
        }, ThreadUtil.BackgroundStrategy.CACHED);
    }

    public static void login(@NonNull LoginCallback callback, String account, String phone, String email, String password) {
        ThreadUtil.submitToCached(()->{
//            try {
                callback.onSuccess(0L,0,"" );
//                LoginRes res = null;
//                if (account != null) {
//                    res = UserRequest.login(account, "", "", EncryptionUtil.string2md5(password));
//                } else if (phone != null) {
//                    res = UserRequest.login("", phone, "", EncryptionUtil.string2md5(password));
//                } else if (email != null) {
//                    res = UserRequest.login("", "", email, EncryptionUtil.string2md5(password));
//                }
//                if (res != null) {
//                    if (StatusCode.LOGIN_ACCOUNT_NOT_EXIST.getCode().equals(res.code)) {
//                        callback.onAccountNotExist();
//                    } else if (StatusCode.LOGIN_PASSWORD_ERROR.getCode().equals(res.code)) {
//                        callback.onPasswordError();
//                    } else if (StatusCode.SUCCESS.getCode().equals(res.code)) {
//                        callback.onSuccess(res.data.userId, res.data.point, res.data.token);
//                    } else {
//                        callback.onErrorWithCode(res.code);
//                    }
//                } else {
//                    callback.onRequestError();
//                }
//            } catch (IOException e) {
//                callback.onIOException("" + e.getMessage());
//                Log.v("NetworkError", "" + e.getMessage());
//            }
        });
    }

    public static void logout(@NonNull CommonCallback callback){
        new Thread(() -> {
            try {
                StatusCodeRes res = UserRequest.logout();
                if (StatusCode.SUCCESS.getCode().equals(res.code)){
                    callback.onSuccess();
                }else callback.onError(res.code);
            } catch (IOException e) {
                callback.onIOException();
                Log.v("NetworkError", "" + e.getMessage());
            }
        }).start();
    }

    public static void queryUserNickname(@NonNull QueryUserNicknameCallback callback, Long userId) {
        new Thread(() -> {
            try {
                if (userId != null) {
                    UserNicknameRes res = UserRequest.queryUserNickname(userId);
                    if (StatusCode.SUCCESS.getCode().equals(res.code)) {
                        callback.onSuccess(res.data.nickname);
                    } else if (StatusCode.QUERY_USER_NO_USER.getCode().equals(res.code)) {
                        callback.onUserNotExist();
                    } else {
                        callback.onError(res.code);
                    }
                }
            } catch (IOException e) {
                Log.v("NetworkError", "" + e.getMessage());
            }
        }).start();
    }

    public static void queryUser(@NonNull QueryUserCallback callback, Long userId) {
        new Thread(() -> {
            try {
                if (userId != null) {
                    UserInfoRes res = UserRequest.queryUserInfo(userId);
                    if (StatusCode.SUCCESS.getCode().equals(res.code)) {
                        User user = new User();
                        user.setUserId(userId);
                        user.setNickname(res.data.nickname);
                        user.setGender(Gender.getByIndex(res.data.gender));
                        user.setAge(res.data.age == -1 ? null : res.data.age);
                        user.setPhone(res.data.phone);
                        user.setEmail(res.data.email);
                        user.setIntroduction(res.data.introduction);
                        callback.onSuccess(user);
                    } else if (StatusCode.QUERY_USER_NO_USER.getCode().equals(res.code)) {
                        callback.onUserNotExist();
                    } else {
                        callback.onError(res.code);
                    }
                }
            } catch (IOException e) {
                Log.v("NetworkError", "" + e.getMessage());
            }
        }).start();
    }

    public static void updateUserInfo(@NonNull UpdateUserInfoCallback callback, @NonNull String nickname, @NonNull Gender gender, @NonNull Integer age, @NonNull String  introduction) {
        new Thread(() -> {
            try {
                StatusCodeRes res = UserRequest.updateUserInfo(nickname, gender, age, introduction);
                if (StatusCode.SUCCESS.getCode().equals(res.code)) {
                    callback.onSuccess();
                } else {
                    callback.onError(res.code);
                }
            } catch (IOException e) {
                Log.v("NetworkError", "" + e.getMessage());
            }
        }).start();
    }

    public static void updateUserAvatar(@NonNull UpdateUserAvatarCallback callback, @NonNull String filePath) {
        new Thread(() -> {
            File file = new File(filePath);
            try {
                StatusCodeRes res = UserRequest.updateUseAvatar(file);
                if (StatusCode.SUCCESS.getCode().equals(res.code)) {
                    callback.onSuccess();
                } else if(StatusCode.IMAGE_TRANSMISSION_ERROR.getCode().equals(res.code)){
                    callback.onTransmissionError();
                }else {
                    callback.onError(res.code);
                }
            } catch (IOException e) {
                Log.v("NetworkError", "" + e.getMessage());
                callback.onIOException("" + e.getMessage());
            }
        }).start();
    }

    public static void queryUserByName(@NonNull QueryUsersCallback callback, @NonNull String nickname){
        new Thread(() -> {
            try {
                UserListRes res = UserRequest.queryUsers(nickname);
                if (StatusCode.SUCCESS.getCode().equals(res.code)) {
                    callback.onSuccess(res.data);
                } else {
                    callback.onError(res.code);
                }
            } catch (IOException e) {
                Log.v("NetworkError", "" + e.getMessage());
            }
        }).start();
    }

    @Deprecated
    private static List<Friend> createExampleFriends() {
        List<Friend> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            list.add(new Friend((long) Math.abs(random.nextInt(90)) + 10, "用户" + EncryptionUtil.string2md5(String.valueOf(random.nextInt(123))).substring(0, 6),
                    Gender.getByIndex(Math.abs(random.nextInt(3))), null));
        }
        return list;
    }

    public static void queryFriends(@NonNull final CommonEntityListCallback<Friend> callback) {
        ThreadUtil.runInBackground(() -> {
            try {
                FriendListRes res = UserRequest.loadAllFriends();
                if (StatusCode.success(res.code)) {
                    List<Friend> friends = new ArrayList<>();
                    for (FriendListRes.Data data : res.data) {
                        friends.add(new Friend(data.userId, data.nickname, data.remark));
                    }
                    friends.addAll(createExampleFriends());
                    callback.onSuccess(friends);
                }
            } catch (IOException e) {
                callback.onIOException();
            }
        }, ThreadUtil.BackgroundStrategy.CACHED);
    }




}
