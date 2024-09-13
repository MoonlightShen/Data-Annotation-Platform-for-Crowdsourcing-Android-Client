package com.echo.dapc.bean.entity;

import com.echo.dapc.bean.enumeration.FileType;
import com.echo.dapc.bean.base.FileInfo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

@Entity(nameInDb = "dt_local_datafile")
public class LocalDataFile {
    @Id(autoincrement = true)
    @Unique
    private Long localDataFileIndex;
    private Integer dataNum;
    private Long importTime;
    private String separator;
    private Boolean localCaching;
    private String cacheFilePath;
    private String fileName;
    private String filePath;
    private Integer fileType;
    private Long fileSize;
    private String fileTag;




    @Generated(hash = 2123900283)
    public LocalDataFile(Long localDataFileIndex, Integer dataNum, Long importTime, String separator,
            Boolean localCaching, String cacheFilePath, String fileName, String filePath,
            Integer fileType, Long fileSize, String fileTag) {
        this.localDataFileIndex = localDataFileIndex;
        this.dataNum = dataNum;
        this.importTime = importTime;
        this.separator = separator;
        this.localCaching = localCaching;
        this.cacheFilePath = cacheFilePath;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.fileTag = fileTag;
    }

    @Generated(hash = 798819938)
    public LocalDataFile() {
    }




    public FileInfo generateFileInfo(){
        return new FileInfo(fileName, filePath, FileType.getByIndex(fileType), fileSize, null, fileTag);
    }

    public Long getLocalDataFileIndex() {
        return localDataFileIndex;
    }

    public void setLocalDataFileIndex(Long localDataFileIndex) {
        this.localDataFileIndex = localDataFileIndex;
    }

    public Integer getDataNum() {
        return dataNum;
    }

    public void setDataNum(Integer dataNum) {
        this.dataNum = dataNum;
    }

    public Long getImportTime() {
        return importTime;
    }

    public void setImportTime(Long importTime) {
        this.importTime = importTime;
    }

    public Boolean getLocalCaching() {
        return localCaching;
    }

    public void setLocalCaching(Boolean localCaching) {
        this.localCaching = localCaching;
    }

    public String getCacheFilePath() {
        return cacheFilePath;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public void setCacheFilePath(String cacheFilePath) {
        this.cacheFilePath = cacheFilePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileTag() {
        return fileTag;
    }

    public void setFileTag(String fileTag) {
        this.fileTag = fileTag;
    }
}
