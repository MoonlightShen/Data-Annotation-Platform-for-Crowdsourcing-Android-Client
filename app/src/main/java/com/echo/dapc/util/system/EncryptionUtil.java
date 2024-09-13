package com.echo.dapc.util.system;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class EncryptionUtil {

    /**
     * Java中的md5
     * @param content 输入的值
     * @return 输出md5加密后的值
     */
    public static String string2md5(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append(0);
            }
            hex.append(Integer.toHexString(b & 0xff));
        }

        return hex.toString();
    }


    /**
     * 根据File对象的内容生成md5
     * @param file 需要提取摘要的文件
     * @return md5
     */
    public static String file2md5(@NonNull File file){
        StringBuilder hex = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            InputStream fis = Files.newInputStream(file.toPath());
            byte[] buffer = new byte[8192];
            int n;
            while ((n = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, n);
            }
            for (byte b : digest.digest()) {
                hex.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
        return hex.toString();
    }

}
