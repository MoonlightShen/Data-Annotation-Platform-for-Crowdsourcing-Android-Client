package com.echo.datatag3.util.system;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;

import com.echo.datatag3.bean.entity.LocalDataFile;
import com.echo.datatag3.bean.enumeration.FileType;
import com.echo.datatag3.bean.logic.DataFile;
import com.echo.datatag3.interfaces.callback.system.CachingDataFileCallback;
import com.echo.datatag3.util.database.DBUtilShop;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mozilla.universalchardet.ReaderFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public final class CacheUtil {

    private static final String DEFAULT_CACHE_FILE_SUFFIX = ".txt";
    private static final String DEFAULT_CACHE_IMAGE_SUFFIX = ".jpg";

    private static File DEFAULT_CACHE_FILE_DIR;
    private static File DEFAULT_CACHE_IMAGE_DIR;



    public static void initCache(Context context) {
        String cachePath = context.getExternalCacheDir().getPath();
        File cacheFolder = new File(cachePath, "Cache");
        if (!cacheFolder.exists()) {
            cacheFolder.mkdirs();
        }
        File cacheFilesFolder = new File(cachePath, "Cache/Files");
        DEFAULT_CACHE_FILE_DIR = cacheFilesFolder;
        if (!cacheFilesFolder.exists()) {
            cacheFilesFolder.mkdirs();
        }
        File cacheImagesFolder = new File(cachePath, "Cache/Images");
        DEFAULT_CACHE_IMAGE_DIR = cacheImagesFolder;
        if (!cacheImagesFolder.exists()) {
            cacheImagesFolder.mkdirs();
        }
    }

    public static boolean existCacheFile(String cacheFileName) {
        File cacheFile = new File(DEFAULT_CACHE_FILE_DIR, cacheFileName + DEFAULT_CACHE_FILE_SUFFIX);
        return cacheFile.exists();
    }

    public static boolean existCacheImage(String cacheFileName) {
        File cacheFile = new File(DEFAULT_CACHE_IMAGE_DIR,  cacheFileName + DEFAULT_CACHE_IMAGE_SUFFIX);
        String a = cacheFile.getAbsolutePath();
        String b = cacheFile.getPath();
        return cacheFile.exists();
    }

    public static File getCacheFile(String cacheFileName) {
        if (!existCacheFile(cacheFileName)) return null;
        return new File(DEFAULT_CACHE_FILE_DIR, cacheFileName + DEFAULT_CACHE_FILE_SUFFIX);
    }

    public static File getCacheImage(String cacheImageName) {
        if (!existCacheImage(cacheImageName)) return null;
        return new File(DEFAULT_CACHE_IMAGE_DIR, cacheImageName + DEFAULT_CACHE_IMAGE_SUFFIX);
    }

    public static void cachingImage(byte[] data, String cacheFileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(DEFAULT_CACHE_IMAGE_DIR, cacheFileName + DEFAULT_CACHE_IMAGE_SUFFIX));
            fos.write(data);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void cachingDataFile(@NonNull CachingDataFileCallback callback, @NonNull DataFile datafile) {
        ThreadUtil.submitToCached(() -> {
            String cachePath = null;
            int dataNum = 0;
            String filePath = datafile.getFileInfo().getPath();
            if (datafile.getLocalCaching()) {
                switch (FileType.getBySuffix(filePath.substring(filePath.lastIndexOf(".") + 1))) {
                    case TXT -> {
                        cachePath = DEFAULT_CACHE_FILE_DIR + "/" + EncryptionUtil.string2md5("txt" + TimeUtil.getCurrentTime()) + DEFAULT_CACHE_FILE_SUFFIX;
                        dataNum = cachingTXTByLines(filePath, cachePath);
                        if (dataNum == -2) {
                            callback.onCachingError();
                            return;
                        }
                    }
                    case XLSX -> {
                        cachePath = DEFAULT_CACHE_FILE_DIR + "/" + EncryptionUtil.string2md5("xlsx" + TimeUtil.getCurrentTime()) + DEFAULT_CACHE_FILE_SUFFIX;
                        dataNum = cachingXLSXByLines(filePath, cachePath);
                        if (dataNum==-2){
                            callback.onFileCorruption();
                            return;
                        }
                    }
                }
                if (cachePath==null||dataNum == -3){
                    callback.onCachingError();
                } else if (dataNum==-1){
                    callback.onFileNotExist();
                }
            }else {
                switch (FileType.getBySuffix(filePath.substring(filePath.lastIndexOf(".") + 1))) {
                    case TXT -> dataNum = readTXTLines(filePath);
                    case XLSX -> dataNum = readXLSXLines(filePath);
                }
                if (dataNum == -3){
                    callback.onCachingError();
                } else if (dataNum==-1){
                    callback.onFileNotExist();
                }
            }
            long fileSize = new File(filePath).length();
            datafile.setCacheFilePath(cachePath);
            datafile.setDataNum(dataNum);
            datafile.getFileInfo().setSize(fileSize);
            try {
                datafile.setDataFileIndex(DBUtilShop.localDataFileDBUtil.insertEntity(new LocalDataFile(null, dataNum,
                        datafile.getImportTime(),datafile.getSeparator(), datafile.getLocalCaching(), cachePath, datafile.getFileInfo().getName(),
                        filePath, datafile.getFileInfo().getType().getIndex(), fileSize, datafile.getFileInfo().getTag())));
                callback.onSuccess(datafile);
            }catch (SQLiteException e){
                callback.onCachingError();
            }
        });
    }

    /**
     * 按行读取TXT文件
     *
     * @param inputFile
     * @return 读取到的数据量 -1表示文件不存在 -3表示读取文件时抛出IO异常
     */
    public static int readTXTLines(String inputFile){
        BufferedReader reader;
        try {
            reader = ReaderFactory.createBufferedReader(new File(inputFile));
        } catch (IOException e) {
            return -1;
        }
        try {
            int line = 0;
            String content;
            while ((content = reader.readLine()) != null) {
                if (content.isEmpty())continue;
                line++;
            }
            reader.close();
            return line;
        } catch (IOException e) {
            return -3;
        }
    }

    /**
     * 按行读取TXT文件并生成缓存文件
     *
     * @param inputFile
     * @param outputFile
     * @return 读取到的数据量 -1表示文件不存在 -2表示打开缓存文件时出现错误 -3表示向缓存文件输出内容时捕获了IO异常
     */
    public static int cachingTXTByLines(String inputFile, String outputFile){
        BufferedReader reader;
        try {
            reader = ReaderFactory.createBufferedReader(new File(inputFile));
        } catch (IOException e) {
            return -1;
        }
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8));
        }catch (FileNotFoundException e){
            return -2;
        }
        try {
            int line = 0;
            String content;
            while ((content = reader.readLine()) != null) {
                if (content.isEmpty())continue;
                if (line>0)writer.write('\n'+content);
                else writer.write(content);
                line++;
            }
            reader.close();
            writer.close();
            return line;
        } catch (IOException e) {
            return -3;
        }
    }

    /**
     * 获取XLSX文件行数
     *
     * @param inputFile
     * @return 读取到的数据量 -1表示文件不存在 -2表示XLSX文件错误或不支持版本 -3表示读取文件时捕获了IO异常
     */
    public static int readXLSXLines(String inputFile){
        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(inputFile);
        } catch (IOException e) {
            return -2;
        }
        try {
            Sheet sheet = workbook.getSheetAt(0);
            int line = 0;
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                String content = cell.getStringCellValue();
                if (content.isEmpty())continue;
                line++;
            }
            workbook.close();
            return line;
        } catch (IOException e) {
            return -3;
        }
    }

    /**
     * 按行读取XLSX文件并生成缓存文件
     *
     * @param inputFile
     * @param outputFile
     * @return 读取到的数据量 -1表示文件不存在 -2表示XLSX文件错误或不支持版本 -3表示向缓存文件输出内容时捕获了IO异常
     */
    public static int cachingXLSXByLines(String inputFile, String outputFile){
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8));
        }catch (FileNotFoundException e){
            return -1;
        }
        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(inputFile);
        } catch (IOException e) {
            return -2;
        }
        try {
            Sheet sheet = workbook.getSheetAt(0);
            int line = 0;
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                String content = cell.getStringCellValue();
                if (content.isEmpty())continue;
                if (line>0)writer.write('\n'+content);
                else writer.write(content);
                line++;
            }
            writer.close();
            workbook.close();
            return line;
        } catch (IOException e) {
            return -3;
        }
    }

}
