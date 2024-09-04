package com.echo.datatag3.util.business;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.echo.datatag3.R;
import com.echo.datatag3.bean.enumeration.Gender;
import com.echo.datatag3.util.network.request.AvatarRequest;
import com.echo.datatag3.util.system.CacheUtil;
import com.echo.datatag3.util.system.EncryptionUtil;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class AvatarUtil {
    public static void loadUserAvatar(long userId, ImageView imageView, boolean forceCache) {
        loadUserAvatar(Gender.UNKNOWN, userId, imageView, forceCache);
    }

    public static void loadUserAvatar(@NonNull Gender gender, long userId, ImageView imageView, boolean forceCache) {
        String fileName = EncryptionUtil.string2md5("user" + userId);
        if (forceCache || !CacheUtil.existCacheImage(fileName)) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    AvatarRequest.requestUserAvatar(response -> CacheUtil.cachingImage(response, fileName), userId);
                } catch (IOException e) {
                    Glide.with(imageView)
                            .load(Gender.isFemale(gender) ? R.drawable.female_default_avatar : R.drawable.male_default_avatar)
                            .into(imageView);
                }
            });

            try {
                future.get(5, TimeUnit.SECONDS);
                // 子线程在超时时间内完成，执行UI操作
                Glide.with(imageView)
                        .load(CacheUtil.getCacheImage(fileName))
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageView);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                Glide.with(imageView)
                        .load(Gender.isFemale(gender) ? R.drawable.female_default_avatar : R.drawable.male_default_avatar)
                        .into(imageView);
            }

        } else {
            Glide.with(imageView)
                    .load(CacheUtil.getCacheImage(fileName))
                    .into(imageView);
        }
    }

    public static void loadTeamAvatar(long teamId, ImageView imageView, boolean forceCache) {
        String fileName = EncryptionUtil.string2md5("team" + teamId);
        if (forceCache || !CacheUtil.existCacheImage(fileName)) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    AvatarRequest.requestTeamAvatar(response -> CacheUtil.cachingImage(response, fileName), teamId);
                } catch (IOException e) {
                    Glide.with(imageView)
                            .load(R.drawable.team_default_avatar)
                            .into(imageView);
                }
            });

            try {
                future.get(5, TimeUnit.SECONDS);
                // 子线程在超时时间内完成，执行UI操作
                Glide.with(imageView)
                        .load(CacheUtil.getCacheImage(fileName))
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageView);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                Glide.with(imageView)
                        .load(R.drawable.team_default_avatar)
                        .into(imageView);
            }

        } else {
            Glide.with(imageView)
                    .load(CacheUtil.getCacheImage(fileName))
                    .into(imageView);
        }
    }

}
