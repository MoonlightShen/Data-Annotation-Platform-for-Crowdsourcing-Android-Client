package com.echo.datatag3.bean.logic;

import com.echo.datatag3.bean.base.FileInfo;
import com.echo.datatag3.bean.entity.LocalDataFile;
import com.echo.datatag3.util.business.InfoUtil;
import com.echo.datatag3.util.database.DBUtilShop;
import com.echo.datatag3.util.system.ThreadUtil;

public final class DataFile {
    private Long dataFileIndex;
    private Long dataFileId;
    private Long ownerId;
    private String separator;
    private Integer dataNum;
    private Long importTime;
    private Boolean localCaching; //本地
    private String cacheFilePath; //本地
    private Boolean uploadCaching; //本地
    private Integer uploadProgress; //本地
    private Long uploadTime;
    private String fileTag;
    private FileInfo fileInfo;

    public DataFile(Long dataFileIndex, Long dataFileId, Long ownerId, String separator, Integer dataNum, Long importTime, Boolean localCaching, String cacheFilePath, Boolean uploadCaching, Integer uploadProgress, Long uploadTime, String fileTag, FileInfo fileInfo) {
        this.dataFileIndex = dataFileIndex;
        this.dataFileId = dataFileId;
        this.ownerId = ownerId;
        this.separator = separator;
        this.dataNum = dataNum;
        this.importTime = importTime;
        this.localCaching = localCaching;
        this.cacheFilePath = cacheFilePath;
        this.uploadCaching = uploadCaching;
        this.uploadProgress = uploadProgress;
        this.uploadTime = uploadTime;
        this.fileTag = fileTag;
        this.fileInfo = fileInfo;
    }

    public static LocalDataFile toLocal(DataFile dataFile) {
        return dataFile == null ? null : new LocalDataFile(dataFile.getDataFileIndex(), dataFile.getDataNum(),
                dataFile.getImportTime(), dataFile.getSeparator(), dataFile.getLocalCaching(), dataFile.getCacheFilePath(),
                dataFile.getFileInfo().getName(), dataFile.getFileInfo().getPath(), dataFile.getFileInfo().getType() == null ? 0 : dataFile.getFileInfo().getType().getIndex(),
                dataFile.getFileInfo().getSize(), dataFile.getFileTag());
    }

    public static DataFile fromLocal(LocalDataFile localDataFile) {
        return localDataFile == null ? null : new DataFile(localDataFile.getLocalDataFileIndex(), null, InfoUtil.getUserId(),
                localDataFile.getSeparator(), localDataFile.getDataNum(), localDataFile.getImportTime(),
                localDataFile.getLocalCaching(), localDataFile.getCacheFilePath(), false,
                0, null, localDataFile.getFileTag(), localDataFile.generateFileInfo());
    }

    public static DataFile fromLocalInBackground(long localDataFileIndex) {
        if (ThreadUtil.isMainThread()) {
            throw new RuntimeException("请在子线程调用该方法");
        } else {
            LocalDataFile localDataFile = DBUtilShop.localDataFileDBUtil.queryEntity(localDataFileIndex);
            if (localDataFile == null) return null;
            return DataFile.fromLocal(localDataFile);
        }
    }

    public DataFile() {
    }


    public String getFileTag() {
        return fileTag;
    }

    public void setFileTag(String fileTag) {
        this.fileTag = fileTag;
    }

    public Long getDataFileId() {
        return dataFileId;
    }

    public void setDataFileId(Long dataFileId) {
        this.dataFileId = dataFileId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getDataFileIndex() {
        return dataFileIndex;
    }

    public void setDataFileIndex(Long dataFileIndex) {
        this.dataFileIndex = dataFileIndex;
    }

    public Integer getDataNum() {
        return dataNum;
    }

    public void setDataNum(Integer dataNum) {
        this.dataNum = dataNum;
    }

    public Boolean getLocalCaching() {
        return localCaching;
    }

    public void setLocalCaching(Boolean localCaching) {
        this.localCaching = localCaching;
    }

    public Long getImportTime() {
        return importTime;
    }

    public void setImportTime(Long importTime) {
        this.importTime = importTime;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getCacheFilePath() {
        return cacheFilePath;
    }

    public void setCacheFilePath(String cacheFilePath) {
        this.cacheFilePath = cacheFilePath;
    }

    public Boolean getUploadCaching() {
        return uploadCaching;
    }

    public void setUploadCaching(Boolean uploadCaching) {
        this.uploadCaching = uploadCaching;
    }

    public Integer getUploadProgress() {
        return uploadProgress;
    }

    public void setUploadProgress(Integer uploadProgress) {
        this.uploadProgress = uploadProgress;
    }

    public Long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }
}
