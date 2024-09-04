package com.echo.datatag3.util.system;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.echo.datatag3.R;
import com.echo.datatag3.bean.base.FileInfo;
import com.echo.datatag3.bean.enumeration.DataType;
import com.echo.datatag3.bean.enumeration.FileType;
import com.echo.datatag3.widget.library.GlideEngine;
import com.luck.picture.lib.basic.PictureSelectionModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.dialog.RemindDialog;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.engine.CropFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnSelectLimitTipsListener;
import com.luck.picture.lib.language.LanguageConfig;
import com.luck.picture.lib.permissions.PermissionConfig;
import com.luck.picture.lib.permissions.PermissionUtil;
import com.luck.picture.lib.style.BottomNavBarStyle;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.style.TitleBarStyle;
import com.luck.picture.lib.utils.DateUtils;
import com.luck.picture.lib.utils.StyleUtils;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropImageEngine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;
import top.zibin.luban.OnRenameListener;

public final class FileUtil {

    public static void selectAvatar(Context context, ActivityResultLauncher<Intent> launcher) {
        PictureSelectorStyle selectorStyle = new PictureSelectorStyle();
        TitleBarStyle whiteTitleBarStyle = new TitleBarStyle();
        whiteTitleBarStyle.setTitleBackgroundColor(ContextCompat.getColor(context, com.luck.picture.lib.R.color.ps_color_white));
        whiteTitleBarStyle.setTitleDrawableRightResource(R.drawable.common_down_black);
        whiteTitleBarStyle.setTitleLeftBackResource(com.luck.picture.lib.R.drawable.ps_ic_black_back);
        whiteTitleBarStyle.setTitleTextColor(ContextCompat.getColor(context, com.luck.picture.lib.R.color.ps_color_black));
        whiteTitleBarStyle.setTitleCancelTextColor(ContextCompat.getColor(context, com.luck.picture.lib.R.color.ps_color_53575e));
        whiteTitleBarStyle.setDisplayTitleBarLine(true);

        BottomNavBarStyle whiteBottomNavBarStyle = new BottomNavBarStyle();
        whiteBottomNavBarStyle.setBottomNarBarBackgroundColor(Color.parseColor("#EEEEEE"));
        whiteBottomNavBarStyle.setBottomPreviewSelectTextColor(ContextCompat.getColor(context, com.luck.picture.lib.R.color.ps_color_53575e));

        whiteBottomNavBarStyle.setBottomPreviewNormalTextColor(ContextCompat.getColor(context, com.luck.picture.lib.R.color.ps_color_9b));
        whiteBottomNavBarStyle.setBottomPreviewSelectTextColor(ContextCompat.getColor(context, com.luck.picture.lib.R.color.ps_color_fa632d));
        whiteBottomNavBarStyle.setCompleteCountTips(false);
        whiteBottomNavBarStyle.setBottomEditorTextColor(ContextCompat.getColor(context, com.luck.picture.lib.R.color.ps_color_53575e));
        whiteBottomNavBarStyle.setBottomOriginalTextColor(ContextCompat.getColor(context, com.luck.picture.lib.R.color.ps_color_53575e));

        SelectMainStyle selectMainStyle = new SelectMainStyle();
        selectMainStyle.setStatusBarColor(ContextCompat.getColor(context, com.luck.picture.lib.R.color.ps_color_white));
        selectMainStyle.setDarkStatusBarBlack(true);
        selectMainStyle.setSelectNormalTextColor(ContextCompat.getColor(context, com.luck.picture.lib.R.color.ps_color_9b));
        selectMainStyle.setSelectTextColor(ContextCompat.getColor(context, com.luck.picture.lib.R.color.ps_color_fa632d));
        selectMainStyle.setPreviewSelectBackground(com.luck.picture.lib.R.drawable.ps_ic_preview_selected);
        selectMainStyle.setSelectBackground(com.luck.picture.lib.R.drawable.ps_checkbox_selector);
        selectMainStyle.setSelectText(com.luck.picture.lib.R.string.ps_done_front_num);
        selectMainStyle.setMainListBackgroundColor(ContextCompat.getColor(context, com.luck.picture.lib.R.color.ps_color_white));

        selectorStyle.setTitleBarStyle(whiteTitleBarStyle);
        selectorStyle.setBottomBarStyle(whiteBottomNavBarStyle);
        selectorStyle.setSelectMainStyle(selectMainStyle);

        PictureSelectionModel selectionModel = PictureSelector.create(context)
                .openGallery(SelectMimeType.ofImage())
                .setSelectorUIStyle(selectorStyle)
                .setImageEngine(GlideEngine.createGlideEngine())
                .setCropEngine(new CropFileEngine() {
                    @Override
                    public void onStartCrop(Fragment fragment, Uri srcUri, Uri destinationUri, ArrayList<String> dataSource, int requestCode) {
                        UCrop.Options options = new UCrop.Options();
                        options.setShowCropFrame(true);
                        options.setShowCropGrid(true);
                        options.setCircleDimmedLayer(true);
                        options.withAspectRatio(1.0f, 1.0f);
                        options.isCropDragSmoothToCenter(true);
                        options.setSkipCropMimeType(PictureMimeType.ofGIF(), PictureMimeType.ofWEBP());
                        options.isForbidCropGifWebp(false);
                        PictureSelectorStyle selectorStyle = new PictureSelectorStyle();
                        if (selectorStyle.getSelectMainStyle().getStatusBarColor() != 0) {
                            SelectMainStyle mainStyle = selectorStyle.getSelectMainStyle();
                            boolean isDarkStatusBarBlack = mainStyle.isDarkStatusBarBlack();
                            int statusBarColor = mainStyle.getStatusBarColor();
                            options.isDarkStatusBarBlack(isDarkStatusBarBlack);
                            if (StyleUtils.checkStyleValidity(statusBarColor)) {
                                options.setStatusBarColor(statusBarColor);
                                options.setToolbarColor(statusBarColor);
                            } else {
                                options.setStatusBarColor(ContextCompat.getColor(context, R.color.default_white));
                                options.setToolbarColor(ContextCompat.getColor(context, R.color.default_white));
                            }
                            TitleBarStyle titleBarStyle = selectorStyle.getTitleBarStyle();
                            if (StyleUtils.checkStyleValidity(titleBarStyle.getTitleTextColor())) {
                                options.setToolbarWidgetColor(titleBarStyle.getTitleTextColor());
                            } else {
                                options.setToolbarWidgetColor(ContextCompat.getColor(context, R.color.default_white));
                            }
                        } else {
                            options.setStatusBarColor(ContextCompat.getColor(context, R.color.default_white));
                            options.setToolbarColor(ContextCompat.getColor(context, R.color.default_white));
                            options.setToolbarWidgetColor(ContextCompat.getColor(context, R.color.default_black));
                        }
                        UCrop uCrop = UCrop.of(srcUri, destinationUri, dataSource);
                        uCrop.withOptions(options);
                        uCrop.setImageEngine(new UCropImageEngine() {
                            @Override
                            public void loadImage(Context context, String url, ImageView imageView) {
                                ToastUtil.toast("loadImage");
                            }

                            @Override
                            public void loadImage(Context context, Uri url, int maxWidth, int maxHeight, OnCallbackListener<Bitmap> call) {
                                Glide.with(context).asBitmap().load(url).override(maxWidth, maxHeight).into(new CustomTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        if (call != null) {
                                            call.onCall(resource);
                                        }
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                        if (call != null) {
                                            call.onCall(null);
                                        }
                                    }
                                });
                            }
                        });
                        uCrop.start(fragment.requireActivity(), fragment, requestCode);
                    }
                })
                .setCompressEngine((CompressFileEngine) (context1, source, call) -> Luban.with(context1).load(source).ignoreBy(5242880).setRenameListener(new OnRenameListener() {
                    @Override
                    public String rename(String filePath) {
                        int indexOf = filePath.lastIndexOf(".");
                        String postfix = indexOf != -1 ? filePath.substring(indexOf) : ".jpg";
                        return DateUtils.getCreateFileName("CMP_") + postfix;
                    }
                }).filter(path -> {
                    if (PictureMimeType.isUrlHasImage(path) && !PictureMimeType.isHasHttp(path)) {
                        return true;
                    }
                    return !PictureMimeType.isUrlHasGif(path);
                }).setCompressListener(new OnNewCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(String source, File compressFile) {
                        if (call != null) {
                            call.onCallback(source, compressFile.getAbsolutePath());
                        }
                    }

                    @Override
                    public void onError(String source, Throwable e) {
                        if (call != null) {
                            call.onCallback(source, null);
                        }
                    }
                }).launch())
                .setSelectLimitTipsListener(new OnSelectLimitTipsListener() {
                    @Override
                    public boolean onSelectLimitTips(Context context, @Nullable LocalMedia media, SelectorConfig config, int limitType) {
                        ToastUtil.toast("请选择一张图片");
                        return true;
                    }
                })
//                .setPermissionDescriptionListener(getPermissionDescriptionListener())
                .setPermissionDeniedListener((fragment, permissionArray, requestCode, call) -> {
                    String tips;
                    if (TextUtils.equals(permissionArray[0], PermissionConfig.CAMERA[0])) {
                        tips = "缺少相机权限\n可能会导致不能使用摄像头功能";
                    } else if (TextUtils.equals(permissionArray[0], Manifest.permission.RECORD_AUDIO)) {
                        tips = "缺少录音权限\n访问您设备上的音频、媒体内容和文件";
                    } else {
                        tips = "缺少存储权限\n访问您设备上的照片、媒体内容和文件";
                    }
                    RemindDialog dialog = RemindDialog.buildDialog(fragment.getContext(), tips);
                    dialog.setButtonText("去设置");
                    dialog.setButtonTextColor(0xFF7D7DFF);
                    dialog.setContentTextColor(0xFF333333);
                    dialog.setOnDialogClickListener(new RemindDialog.OnDialogClickListener() {
                        @Override
                        public void onClick(View view) {
                            PermissionUtil.goIntentSetting(fragment, requestCode);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                })
                .isPageSyncAlbumCount(true)
//                .setQueryFilterListener(new OnQueryFilterListener() {
//                    @Override
//                    public boolean onFilter(LocalMedia media) {
//                        return false;
//                    }
//                })
                //.setExtendLoaderEngine(getExtendLoaderEngine())
                .setSelectionMode(SelectModeConfig.SINGLE)
                .setLanguage(LanguageConfig.CHINESE)
//                .setQuerySortOrder(cb_query_sort_order.isChecked() ? MediaStore.MediaColumns.DATE_MODIFIED + " ASC" : "")
                .isFastSlidingSelect(true)
                //.setOutputCameraImageFileName("luck.jpeg")
                //.setOutputCameraVideoFileName("luck.mp4")
                .isPreviewFullScreenMode(true)
                .isPreviewZoomEffect(true)
                .isPreviewImage(true)
                .setGridItemSelectAnimListener((view, isSelected) -> {
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(
                            ObjectAnimator.ofFloat(view, "scaleX", isSelected ? 1F : 1.12F, isSelected ? 1.12f : 1.0F),
                            ObjectAnimator.ofFloat(view, "scaleY", isSelected ? 1F : 1.12F, isSelected ? 1.12f : 1.0F)
                    );
                    set.setDuration(350);
                    set.start();
                })
                .setSelectAnimListener(view -> {
                    Animation animation = AnimationUtils.loadAnimation(context, com.luck.picture.lib.R.anim.ps_anim_modal_in);
                    view.startAnimation(animation);
                    return animation.getDuration();
                })
                .isMaxSelectEnabledMask(true)
                .setMaxSelectNum(1)
                .setMinSelectNum(1);
        selectionModel.forResult(launcher);
    }

    /**
     * 根据Uri获取文件绝对路径，解决Android4.4以上版本Uri转换
     *
     * @param context
     * @param imageUri
     */
    public static String getFileAbsolutePath(Context context, Uri imageUri) {
        if (context == null || imageUri == null) {
            return null;
        }

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
            return getRealFilePath(context, imageUri);
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return uriToFileApiQ(context, imageUri);
        } else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri)) {
                return imageUri.getLastPathSegment();
            }
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    //此方法 只能用于4.4以下的版本
    private static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            String[] projection = {MediaStore.Images.ImageColumns.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

//            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static void selectFiles(@NonNull ActivityResultLauncher<Intent> launcher ,@NonNull DataType dataType) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, FileType.getSupportedFiles(dataType));
        launcher.launch(intent);
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    /**
     * Android 10 以上适配 另一种写法
     *
     * @param context
     * @param uri
     * @return
     */
    @SuppressLint("Range")
    public static String getFileFromContentUri(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME};
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uri, filePathColumn, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            try {
                filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
                return filePath;
            } catch (Exception e) {
            } finally {
                cursor.close();
            }
        }
        return "";
    }

    /**
     * Android 10 以上适配
     *
     * @param context
     * @param uri
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static String uriToFileApiQ(Context context, Uri uri) {
        File file = null;
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(context.getExternalCacheDir().getAbsolutePath(), displayName);
                    FileOutputStream fos = new FileOutputStream(cache);
                    FileUtils.copy(is, fos);
                    file = cache;
                    fos.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file.getAbsolutePath();
    }


}
