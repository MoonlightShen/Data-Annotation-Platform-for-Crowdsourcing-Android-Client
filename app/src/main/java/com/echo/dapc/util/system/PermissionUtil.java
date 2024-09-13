package com.echo.dapc.util.system;

import android.content.Context;

import androidx.annotation.NonNull;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

public final class PermissionUtil {

    public static boolean checkFileReadAndWritePermission(Context context) {
        return XXPermissions.isGranted(context, Permission.READ_MEDIA_IMAGES, Permission.READ_MEDIA_VIDEO, Permission.READ_MEDIA_AUDIO);
    }

    public static void requestFileReadAndWritePermission(Context context, @NonNull OnPermissionCallback callback) {
        XXPermissions.with(context)
                .permission(Permission.READ_MEDIA_IMAGES)
                .permission(Permission.READ_MEDIA_VIDEO)
                .permission(Permission.READ_MEDIA_AUDIO)
                .request(callback);
    }
}
