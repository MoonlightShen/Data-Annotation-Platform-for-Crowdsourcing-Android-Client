package com.echo.datatag3.util.business;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import androidx.annotation.NonNull;

import com.echo.datatag3.bean.entity.FriendRequest;
import com.echo.datatag3.bean.enumeration.StatusCode;
import com.echo.datatag3.bean.enumeration.ValidationStatus;
import com.echo.datatag3.interfaces.callback.friendapplication.LoadAllFriendApplicationsCallback;
import com.echo.datatag3.interfaces.callback.user.HandleFriendRequestCallback;
import com.echo.datatag3.interfaces.callback.user.PostFriendRequestCallback;
import com.echo.datatag3.util.database.DBUtilShop;
import com.echo.datatag3.util.network.request.UserRequest;
import com.echo.datatag3.util.network.response.common.StatusCodeRes;
import com.echo.datatag3.util.network.response.friendrequest.FriendRequestListRes;

import java.io.IOException;
import java.util.List;

public final class FriendRequestUtil {
    public static void loadAllFriendApplications(LoadAllFriendApplicationsCallback callback){
        new Thread(()-> {
            try {
                List<FriendRequest> requests = DBUtilShop.friendRequestDBUtil.loadAllEntities();
                FriendRequestListRes res1 = UserRequest.getFriendRequestToMe();
                if (StatusCode.SUCCESS.getCode().equals(res1.code)){
                   for (FriendRequestListRes.Data data:res1.data){
                       requests.add(new FriendRequest(data.requestId, data.requesterId,
                               data.userId, data.validateContent, ValidationStatus.getByIndex(data.validationStatus),
                               null, data.requestTime));
                   }
                }
                FriendRequestListRes res2 = UserRequest.getMyFriendRequest();
                if (StatusCode.SUCCESS.getCode().equals(res2.code)){
                    for (FriendRequestListRes.Data data:res2.data){
                        requests.add(new FriendRequest(data.requestId, data.requesterId,
                                data.userId, data.validateContent, ValidationStatus.getByIndex(data.validationStatus),
                                null, data.requestTime));
                    }
                }
                callback.onSuccess(requests);
            }catch (SQLiteException e){
                callback.onSQLiteError();
            } catch (IOException e) {
                callback.onIOException();
            }
        }).start();
    }

    public static void postFriendRequest(@NonNull PostFriendRequestCallback callback, long userId, @NonNull String validateContent){
        new Thread(() -> {
            try {
                StatusCodeRes res = UserRequest.postFriendRequest(userId, validateContent);
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

    public static void handleFriendRequest(@NonNull HandleFriendRequestCallback callback, long requestId, String reply, String remark){
        new Thread(() -> {
            try {
                if (reply==null&&remark==null){
                    StatusCodeRes res = UserRequest.ignoreFriendRequest(requestId);
                    if (StatusCode.SUCCESS.getCode().equals(res.code)){
                        callback.onIgnore();
                    }else callback.onError(res.code);
                }else if (reply == null){
                    StatusCodeRes res = UserRequest.agreeFriendRequest(requestId, remark);
                    if (StatusCode.SUCCESS.getCode().equals(res.code)){
                        callback.onAgree();
                    }else callback.onError(res.code);
                }else {
                    StatusCodeRes res = UserRequest.refuseFriendRequest(requestId, reply);
                    if (StatusCode.SUCCESS.getCode().equals(res.code)){
                        callback.onRefuse();
                    }else callback.onError(res.code);
                }
            } catch (IOException e) {
                Log.v("NetworkError", "" + e.getMessage());
            }
        }).start();
    }
}
